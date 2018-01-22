var express = require('express');
var path = require('path');
var formidable = require('formidable');
var fs = require('fs');
var tmp = require('tmp');
var extract = require('extract-zip');
var child_process = require('child_process');
var randomstring = require("randomstring");
var nodemailer = require('nodemailer');
const glob = require('glob');
const xml2js = require('xml2js');
var jsel = require('jsel');
var resultFile = 'result.csv';
var resultFileTemp = 'resulttemp.csv';
var json2csv = require('json2csv');
var jsonQuery = require('json-query')
var si2 = require('./SI2.json');
var util = require('util');
var fieldNames = [];

var mavenhome = '/opt/apache-maven-3.5.0/';
var isScala = true;

function getDirectories(srcpath) {
    return fs.readdirSync(srcpath).filter(function (file) {
        return fs.statSync(path.join(srcpath, file)).isDirectory();
    });
}

function precisionRound(number, precision) {
    var factor = Math.pow(10, precision);
    return Math.round(number * factor) / factor;
  }
  


function people(filename /*17011558 */ ) {
    var key = path.posix.basename(filename).replace('CP1_', '').replace('.zip', '')

    /*
  people.NUMERO;
  people.NOM;
  people.EMAIL;
  people.GROUPE;
*/
    return jsonQuery('[NUMERO=' + key + ']', {
        data: si2
    }).value;

}


function parseSync(xml) {

    // Like wtf? Why is this using a callback when it's not async? So pointless.
    var error = null;
    var json = null;
    var parser = new xml2js.Parser({
        explicitArray: false
    });
    parser.parseString(xml, function (innerError, innerJson) {

        error = innerError;
        json = innerJson;
    });

    if (error) {

        throw error;
    }

    if (!error && !json) {

        throw new Error('The callback was suddenly async or something.');
    }

    return json;
}


if (process.argv[2] < 3) {
    console.log("Please provide the folder to analyse");
    process.exit;
}
var s = '' + process.argv[2];
console.log(s);

//fs.writeFileSync(resultFile,'Nom étudiant;nombre de tests executés,nombre de tests en erreur;nombre de tests non exécutés;nombre de tests en échec;'+
//'nombre de style (scalastyle) en warning;nombre de style (scalastyle) en erreur\n');


var resultsjson = [];



async function extractanddo(file, tmpfolder, tmpfolder1) {
    return new Promise((resolve, reject) => {
        extract(file, {
            dir: tmpfolder.name
        }, function (err) {
            var note = 0;
            var testnames = ['idEtudiant', 'nom', 'prenom', 'mail', 'groupe', 'errorcode', 'note'];
            var resultjson = {};
            var etudiant = people(file);
            if (etudiant != null) {
                resultjson['idEtudiant'] = etudiant.NUMERO;
                resultjson['nom'] = etudiant.NOM;
                resultjson['prenom'] = etudiant.PRENOM;
                resultjson['mail'] = etudiant.EMAIL;
                resultjson['groupe'] = etudiant.GROUPE;
            } else {
                resultjson['idEtudiant'] = file;
            }


            if (err) {
                resultjson['errorcode'] = 7;
            } else {
                var error = false;
                var errorcode = 0;

                var history = child_process.execSync('cp -r templateProjet/* ' + tmpfolder1.name, {
                    encoding: 'utf8'
                });
                var dirProjet = getDirectories(tmpfolder.name)[0];

                if (dirProjet != null) {
                    if (isScala) {
                        try {
                            var history = child_process.execSync('cp -r ' + tmpfolder.name + '/' + dirProjet + '/src/* ' + tmpfolder1.name + '/src/main/scala/', {
                                encoding: 'utf8'
                            });
                        } catch (e) {
                            error = true;
                            errorcode = 1;
                        }
                        /*try {

                            history = child_process.execSync('cp -r ' + tmpfolder.name + '/' + dirProjet + '/tests/* ' + tmpfolder1.name + '/src/test/scala/', {
                                encoding: 'utf8'
                            });
                        } catch (e) {
                            errorcode = 2;
                            error = true;
                        }*/
                        try {
                            if (fs.existsSync(path.join(tmpfolder.name + '/' + dirProjet + '/img'))) {
                                history = child_process.execSync('cp -r ' + tmpfolder.name + '/' + dirProjet + '/img ' + tmpfolder1.name + '/src/main/resources/', {
                                    encoding: 'utf8'
                                });
                            }
                        } catch (e) {
                            errorcode = 3;
                            error = true;
                        }

                        try {
                            history = child_process.execSync('cp -r ' + tmpfolder.name + '/' + dirProjet + '/*.jar ' + tmpfolder1.name + '/lib/', {
                                encoding: 'utf8'
                            });
                        } catch (e) {
                            error = true;
                            errorcode = 4;
                        }
                    } else {
                        try {
                            var history = child_process.execSync('cp -r ' + tmpfolder.name + '/' + dirProjet + '/src/* ' + tmpfolder1.name + '/src/main/java/', {
                                encoding: 'utf8'
                            });
                        } catch (e) {
                            errorcode = 1;
                            error = true;

                        }
                    }
                } else {
                    errorcode = 7;
                    error = true;
                }
                ///opt/apache-maven-3.2.3/bin/mvn -f /home/barais/git/projetDelfine/pom.xml  clean test
                if (!error) {
                    var files = glob.sync(path.join(tmpfolder1.name, '/lib/*.jar'));
                    var replacement = '';
                    var libn = 0;
                    files.forEach(function (f) {
                        replacement = replacement + "<dependency><artifactId>delfinelib" + libn + "</artifactId><groupId>delfinelib</groupId><version>1.0</version><scope>system</scope><systemPath>${project.basedir}/lib/" + path.basename(f) + "</systemPath></dependency>"
                        libn = libn + 1;
                    });
                    data = fs.readFileSync(path.join(tmpfolder1.name + '/pom.xml'), 'utf8');
                    var result = data.replace('<!--deps-->', replacement);
                    fs.writeFileSync(path.join(tmpfolder1.name + '/pom.xml'), result, 'utf8');

                    try {
                        var history = child_process.execSync(mavenhome + '/bin/mvn -f' + tmpfolder1.name + '/pom.xml clean scalastyle:check test -Dmaven.test.failure.ignore=true', {
                            encoding: 'utf8'
                        });
                    } catch (e) {
                        error = true;
                        errorcode = 5;
                    }
                }

                resultjson['errorcode'] = errorcode;

                if (!error) {


                    var ntests = 0;
                    var nerrors = 0;
                    var nskips = 0;
                    var nfailures = 0;

                    var files = glob.sync(path.join(tmpfolder1.name, '/target/surefire-reports/*.xml'));


                    files.forEach(function (f) {
                        var data = fs.readFileSync(f);
                        var xml = parseSync(data);
                        xml.testsuite.testcase.forEach(function (testcase1) {
                            var name = testcase1.$.name;
                            /*var skippedname = 'skipped' + name
                            var errorsname = 'error' + name
                            var failuresname = 'failure' + name
                            testnames.push(skippedname);
                            testnames.push(errorsname);
                            testnames.push(failuresname);*/
                            testnames.push(name);
                        });
                    });
                    var defineFailure = '';
                    files.forEach(function (f) {
                        var data = fs.readFileSync(f);
                        var xml = parseSync(data);                        
                        xml.testsuite.testcase.forEach(function (testcase1) {
                            var name = testcase1.$.name;
                            console.log(name);
                            resultjson[name] = 0;
                            if (testcase1.skipped != null) {
                                resultjson[name] = resultjson[name] + 1;
                            }
                            if (testcase1.error != null) {
                                resultjson[name] = resultjson[name] + 1;
                            }
                            if (testcase1.failure != null) {
                                resultjson[name] = resultjson[name] + 1;
                            }
                            /*var skippedname = 'skipped' + name
                            var errorsname = 'error' + name
                            var failuresname = 'failure' + name
                            if (testcase1.skipped != null) {
                                resultjson[skippedname] = 1;
                            } else {
                                resultjson[skippedname] = 0;
                            }
                            if (testcase1.error != null) {
                                resultjson[errorsname] = 1;
                            } else {
                                resultjson[errorsname] = 0;
                            }
                            if (testcase1.failure != null) {
                                resultjson[failuresname] = 1;
                            } else {
                                resultjson[failuresname] = 0;
                            }*/

                            if (name.includes('Defined')) {
                                if (resultjson[name] == 0) {
                                    note = note + 1;
                                } else {
                                    defineFailure = defineFailure + name + ',';
                                }
                            }
                        });
                        console.log(defineFailure);
                        xml.testsuite.testcase.forEach(function (testcase1) {
                            var name = testcase1.$.name;
                            if (!name.includes('Defined')) {
                                if (name.includes('max') && !defineFailure.includes('max')) {
                                    var malus = resultjson[name] * 0.33
                                    if (malus > 1)
                                        note = note -1;
                                    else 
                                        note = note -malus;
                                } else if (name.includes('estMultiple') && !defineFailure.includes('estMultiple')) {
                                    var malus = resultjson[name] * 0.17
                                    if (malus > 1)
                                        note = note -1;
                                    else 
                                        note = note -malus;
                                } else if (name.includes('xor1') && !defineFailure.includes('xor1')) {
                                    var malus = resultjson[name] * 0.2
                                    if (malus > 1)
                                        note = note -1;
                                    else 
                                        note = note -malus;
                                } else if (name.includes('xor2') && !defineFailure.includes('xor2')) {
                                    var malus = resultjson[name] * 0.2
                                    if (malus > 1)
                                        note = note -1;
                                    else 
                                        note = note -malus;
                                } else if (name.includes('signe') && !defineFailure.includes('signe')) {
                                    var malus = resultjson[name] * 0.15
                                    if (malus > 1)
                                        note = note -1;
                                    else 
                                        note = note -malus;
                                }

                            }
                        });


                        ntests = ntests + parseInt(xml.testsuite.$.tests);
                        nerrors = nerrors + parseInt(xml.testsuite.$.errors);
                        nskips = nskips + parseInt(xml.testsuite.$.skipped);
                        nfailures = nfailures + parseInt(xml.testsuite.$.failures);


                    });

                    resultjson.skippedtotal = parseInt(nskips)
                    resultjson.failurestotal = parseInt(nfailures)
                    resultjson.errorstotal = parseInt(nerrors)
                    resultjson.teststotal = parseInt(ntests)
                    console.log(note);
                    var data = fs.readFileSync(path.join(tmpfolder1.name, '/scalastyle_config.xml'));
                    var xml = parseSync(data);

                    xml.scalastyle.check.forEach(function (check) {
                        if (check.$.enabled) {
                            if (check.$.level === "warning") {
                                testnames.push('warn(' + check.$.class + ')');
                                resultjson['warn(' + check.$.class + ')'] = 0;
                            } else if (check.$.level === "error") {
                                testnames.push('err(' + check.$.class + ')');
                                resultjson['err(' + check.$.class + ')'] = 0;
                            }
                        }
                    });
                    var data = fs.readFileSync(path.join(tmpfolder1.name, '/scalastyle-output.xml'));
                    var xml = parseSync(data);
                    var malus1 = 0;
                    var malus2 = 0;
                    if (xml.checkstyle.file != null && util.isArray(xml.checkstyle.file.error)) {
                        xml.checkstyle.file.error.forEach(function (error1) {
                            if (error1.$.severity === 'warning') {
                                resultjson['warn(' + error1.$.source + ')'] = resultjson['warn(' + error1.$.source + ')'] + 1;
                                if ('org.scalastyle.scalariform.ScalaDocChecker' === error1.$.source ||
                                    'org.scalastyle.scalariform.ReturnChecker' == error1.$.source) {
                                    malus1 = 1;
                                } else if ('org.scalastyle.scalariform.RedundantIfChecker' === error1.$.source ||
                                    'org.scalastyle.scalariform.SimplifyBooleanExpressionChecker' === error1.$.source) {
                                    //
                                } else if ('org.scalastyle.scalariform.NotImplementedErrorUsage' === error1.$.source) {

                                } else if (['org.scalastyle.file.HeaderMatchesChecker',
                                        'org.scalastyle.file.WhitespaceEndOfLineChecker',
                                        'org.scalastyle.scalariform.EqualsHashCodeChecker',
                                        'org.scalastyle.scalariform.IllegalImportsChecker',
                                        'org.scalastyle.scalariform.MagicNumberChecker',
                                        'org.scalastyle.scalariform.NoCloneChecker',
                                        'org.scalastyle.scalariform.NoFinalizeChecker',
                                        'org.scalastyle.scalariform.CovariantEqualsChecker',
                                        'org.scalastyle.file.RegexChecker',
                                        'org.scalastyle.scalariform.NumberOfTypesChecker',
                                        'org.scalastyle.scalariform.CyclomaticComplexityChecker',
                                        'org.scalastyle.scalariform.IfBraceChecker',
                                        'org.scalastyle.scalariform.NumberOfMethodsInTypeChecker',
                                        'org.scalastyle.file.NewLineAtEofChecker',
                                        'org.scalastyle.file.NoNewLineAtEofChecker',
                                        'org.scalastyle.scalariform.WhileChecker',
                                        'org.scalastyle.scalariform.VarLocalChecker',
                                        'org.scalastyle.scalariform.TokenChecker',
                                        'org.scalastyle.scalariform.DeprecatedJavaChecker',
                                        'org.scalastyle.scalariform.UnderscoreImportChecker'
                                    ].includes(error1.$.source)) {

                                } else {
                                    malus2 = 0.5;
                                }
                            } else if (error1.$.severity === 'error') {
                                resultjson['err(' + error1.$.source + ')'] = resultjson['err(' + error1.$.source + ')'] + 1;
                                note = 0;
                            }
                        });
                    } else {
                        if (xml.checkstyle.file != null && xml.checkstyle.file.error.$.severity === 'warning') {
                            resultjson['warn(' + xml.checkstyle.file.error.$.source + ')'] = resultjson['warn(' + xml.checkstyle.file.error.$.source + ')'] + 1;
                            var error1 = xml.checkstyle.file.error;
                            if ('org.scalastyle.scalariform.ScalaDocChecker' === error1.$.source ||
                                'org.scalastyle.scalariform.ReturnChecker' == error1.$.source) {
                                malus1 = 1;
                            } else if ('org.scalastyle.scalariform.RedundantIfChecker' === error1.$.source ||
                                'org.scalastyle.scalariform.SimplifyBooleanExpressionChecker' === error1.$.source) {
                                //
                            } else if ('org.scalastyle.scalariform.NotImplementedErrorUsage' === error1.$.source) {

                            } else if (['org.scalastyle.file.HeaderMatchesChecker',
                                    'org.scalastyle.file.WhitespaceEndOfLineChecker',
                                    'org.scalastyle.scalariform.EqualsHashCodeChecker',
                                    'org.scalastyle.scalariform.IllegalImportsChecker',
                                    'org.scalastyle.scalariform.MagicNumberChecker',
                                    'org.scalastyle.scalariform.NoCloneChecker',
                                    'org.scalastyle.scalariform.NoFinalizeChecker',
                                    'org.scalastyle.scalariform.CovariantEqualsChecker',
                                    'org.scalastyle.file.RegexChecker',
                                    'org.scalastyle.scalariform.NumberOfTypesChecker',
                                    'org.scalastyle.scalariform.CyclomaticComplexityChecker',
                                    'org.scalastyle.scalariform.IfBraceChecker',
                                    'org.scalastyle.scalariform.NumberOfMethodsInTypeChecker',
                                    'org.scalastyle.file.NewLineAtEofChecker',
                                    'org.scalastyle.file.NoNewLineAtEofChecker',
                                    'org.scalastyle.scalariform.WhileChecker',
                                    'org.scalastyle.scalariform.VarLocalChecker',
                                    'org.scalastyle.scalariform.TokenChecker',
                                    'org.scalastyle.scalariform.DeprecatedJavaChecker',
                                    'org.scalastyle.scalariform.UnderscoreImportChecker'
                                ].includes(error1.$.source)) {

                            } else {
                                malus1 = 0.5;
                            }

                        } else if (xml.checkstyle.file != null && xml.checkstyle.file.error.$.severity === 'error') {
                            resultjson['err(' + xml.checkstyle.file.error.$.source + ')'] = resultjson['err(' + xml.checkstyle.file.error.$.source + ')'] + 1;
                            note = 0;
                        }
                    }

                    if (note>0){
                        note = note -malus1 -malus2;
                    }
                    if (note<0){
                        note = 0;
                    }
                    var dom = jsel(xml);
                    var warningstyle = dom.selectAll('(//*/@severity)').filter(word => word === 'warning').length
                    var errorstyle = dom.selectAll('(//*/@severity)').filter(word => word === 'error').length

                    testnames.push('skippedtotal');
                    testnames.push('failurestotal');
                    testnames.push('errorstotal');
                    testnames.push('teststotal');

                    testnames.push('styleerrtotal');
                    testnames.push('stylewarntotal');
                    resultjson['stylewarntotal'] = warningstyle;
                    resultjson['styleerrtotal'] = errorstyle;
                    note = precisionRound(note*4, 0)/4.0

                    resultjson['note'] = note;






                } else {
                    resultjson['note'] = 0;

                    if (errorcode == 0) {
                        console.log('Projet en erreur ' + file);
                    } else if (errorcode == 1) {
                        console.log('Projet en erreur pas de sources ' + file);
                    } else if (errorcode == 2) {
                        console.log('Projet en erreur pas de tests ' + file);
                    } else if (errorcode == 3) {
                        console.log('Projet en erreur pas d\'images ' + file);
                    } else if (errorcode == 4) {
                        console.log('Projet en erreur pas de librairies ' + file);
                    } else if (errorcode == 5) {
                        console.log('Projet en erreur ne compile pas. Erreur exécution maven  ' + file);
                    } else if (errorcode == 6) {
                        console.log('Projet en erreur, pas d\'exécution du check syntaxique ' + file);
                    } else if (errorcode == 7) {
                        console.log('Bad zip ' + file);
                    }

                }
            }
            resultsjson.push(resultjson);
            if (fieldNames.length == 0) {
                fieldNames = testnames;
            }

            var history = child_process.execSync('rm -rf ' + tmpfolder.name + ' ' + tmpfolder1.name, {
                encoding: 'utf8'
            });

            var result = json2csv({
                data: resultsjson,
                fields: fieldNames
            });
            fs.writeFileSync(resultFileTemp, result);
            resolve();
        });
    });
}

async function main() {


    var zipfiles = glob.sync(path.join(s, '/*.zip'));

    for (let file of zipfiles) {
        console.log('file ' + file);
        if (path.extname(file).substring(1) == 'zip') {
            var tmpfolder = tmp.dirSync();
            var tmpfolder1 = tmp.dirSync();
            console.log('Dir1: ', tmpfolder.name);
            console.log('Dir2: ', tmpfolder1.name);

            var r = await extractanddo(file, tmpfolder, tmpfolder1);
        }
    };

    var result = json2csv({
        data: resultsjson,
        fields: fieldNames
    });
    fs.writeFileSync(resultFile, result);


}
main();