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
var lineByLine = require('n-readlines');
var md5 = require('md5');


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
    var key = path.posix.basename(filename).replace('CP3_', '').replace('.zip', '')

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
            var sessionnumero = '';
            //session1
            var val = {};
            var fun = {};
            var rec = {};

            val['session1'] = ['d1', 'd2', 'd3']
            fun['session1'] = ['reference', 'voyagePro']
            rec['session1'] = ['double', 'compte']

            val['session2'] = ['t1', 't2', 't3']
            fun['session2'] = ['ticketValide', 'prixTicket']
            rec['session2'] = ['incremente', 'nbZeros']

            val['session3'] = ['v1', 'v2', 'v3']
            fun['session3'] = ['estToutTerrain', 'prixTotal']
            rec['session3'] = ['listeLongueurs', 'nth']

            val['session4'] = ['p1', 'p2', 'p3']
            fun['session4'] = ['sujetDuPost', 'trending']
            rec['session4'] = ['listeParites', 'queLesVrais']

            val['session5'] = ['r1', 'r2', 'r3']
            fun['session5'] = ['sonRendu', 'note']
            rec['session5'] = ['opposes', 'sommeEgalA']

            val['session6'] = ['c1', 'c2', 'c3']
            fun['session6'] = ['aGagne', 'niveauSup']
            rec['session6'] = ['double', 'renverse']

            val['session7'] = ['v1', 'v2', 'v3']
            fun['session7'] = ['estToutTerrain', 'prixTotal']
            rec['session7'] = ['opposes', 'nbZeros']

            val['session8'] = ['t1', 't2', 't3']
            fun['session8'] = ['ticketValide', 'prixTicket']
            rec['session8'] = ['elementsPositifs', 'nth']

            val['session9'] = ['p1', 'p2', 'p3']
            fun['session9'] = ['sujetDuPost', 'trending']
            rec['session9'] = ['listeLongueurs', 'compte']

            val['session10'] = ['d1', 'd2', 'd3']
            fun['session10'] = ['reference', 'voyagePro']
            rec['session10'] = ['elementsPositifs', 'queLesVrais']

            val['session11'] = ['c1', 'c2', 'c3']
            fun['session11'] = ['aGagne', 'niveauSup']
            rec['session11'] = ['elementsPairs', 'sommeEgalA']

            val['session12'] = ['r1', 'r2', 'r3']
            fun['session12'] = ['sonRendu', 'note']
            rec['session12'] = ['flip', 'renverse']

            var cval = {};
            var cfun = {};
            var crec = {};

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
                note = 0;
                resultjson['errorcode'] = 7;
            } else {
                var error = false;
                var errorcode = 0;

                var dirProjet = getDirectories(tmpfolder.name)[0];
                if (dirProjet != 'CP3') {
                    console.log('dirProjet ' + dirProjet)
                    try {
                        var history = child_process.execSync('mv ' + tmpfolder.name + '/' + dirProjet + ' ' + tmpfolder.name + '/CP3', {
                            encoding: 'utf8'
                        });
                    } catch (e) {
                        console.log(e);
                    }
                }
                dirProjet = 'CP3';

                var liner = new lineByLine(tmpfolder.name + '/CP3/src/.fileid');
                var line;
                var lineNumber = 0;
                while (lineNumber < 6 && (line = liner.next())) {
                    if (lineNumber == 0) {
                        resultjson['id_fileid'] = line.toString('ascii');
                        testnames.push('id_fileid');
                    } else if (lineNumber == 1) {
                        resultjson['name_fileid'] = line.toString('ascii');
                        testnames.push('name_fileid');
                    } else if (lineNumber == 2) {
                        resultjson['hash1_fileid'] = line.toString('ascii');
                        testnames.push('hash1_fileid');
                    } else if (lineNumber == 3) {
                        resultjson['date_fileid'] = line.toString('ascii');
                        testnames.push('date_fileid');
                    } else if (lineNumber == 4) {
                        sessionnumero = line.toString('ascii').replace(' ', '');
                        resultjson['sessionnumero'] = sessionnumero;
                        testnames.push('sessionnumero');
                    } else if (lineNumber == 5) {
                        resultjson['hash2_fileid'] = line.toString('ascii');
                        testnames.push('hash2_fileid');
                    }
                    lineNumber++;
                }
//                sessionnumero = line.toString('ascii').replace(' ', '');
                cval = val[sessionnumero];
                cfun = fun[sessionnumero];
                crec = rec[sessionnumero];

                console.log(sessionnumero)
//                testnames.push('sessionnumero');
//                resultjson['sessionnumero'] = sessionnumero;
                var history = child_process.execSync('cp -r templateProjectCP3/CP3_' + sessionnumero + '/* ' + tmpfolder1.name, {
                    encoding: 'utf8'
                });

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

                var md5files = glob.sync(path.join(tmpfolder1.name, '/src/main/scala/fr/istic/si2/checkpoint3/*.scala'));
                md5files.forEach(function (f) {
                    var data = fs.readFileSync(f);
                    resultjson['md5' + path.posix.basename(f)] = md5(data);
                    testnames.push('md5' + path.posix.basename(f));
                });


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
                            testnames.push(name);
                        });
                    });
                    var defineFailure = '';
                    var testsuitemalus = {};
                    testsuitemalus[cval[0]] = 0;
                    testsuitemalus[cval[1]] = 0;
                    testsuitemalus[cval[2]] = 0;
                    testsuitemalus[cfun[0]] = 0;
                    testsuitemalus[cfun[1]] = 0;
                    testsuitemalus[crec[0]] = 0;
                    testsuitemalus[crec[1]] = 0;

                    files.forEach(function (f) {
                        var data = fs.readFileSync(f);
                        var xml = parseSync(data);
                        xml.testsuite.testcase.forEach(function (testcase1) {
                            var name = testcase1.$.name;
                            //console.log(name);
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
                            if (name.includes('Defined')) {
                                if (resultjson[name] == 0) {
                                    if (name.includes(cval[0]) || name.includes(cval[1]) || name.includes(cval[2])) {
                                        note = note + 1;
                                    } else if (name.includes(cfun[0]) || name.includes(cfun[1])) {
                                        note = note + 3;
                                    } else if (name.includes(crec[0])) {
                                        note = note + 6;
                                    } else if (name.includes(crec[1])) {
                                        note = note + 9;
                                    } else {
                                        console.log('oups ' + name);
                                    }
                                } else {
                                    defineFailure = defineFailure + name + ',';
                                }
                            }
                        });
                        console.log('defineFailure:' + defineFailure);
                        console.log('note:' + note);


                        xml.testsuite.testcase.forEach(function (testcase1) {
                            var name = testcase1.$.name;
                            if (!name.includes('Defined')) {
                                if (name.includes(cval[0]) && !defineFailure.includes(cval[0])) {
                                    testsuitemalus[cval[0]] = testsuitemalus[cval[0]] + (resultjson[name])
                                } else if (name.includes(cval[1]) && !defineFailure.includes(cval[1])) {
                                    testsuitemalus[cval[1]] = testsuitemalus[cval[1]] + (resultjson[name])
                                } else if (name.includes(cval[2]) && !defineFailure.includes(cval[2])) {
                                    testsuitemalus[cval[2]] = testsuitemalus[cval[2]] + (resultjson[name])
                                } else if (name.includes(cfun[0]) && !defineFailure.includes(cfun[0])) {
                                    testsuitemalus[cfun[0]] = testsuitemalus[cfun[0]] + (resultjson[name] * 1.5)
                                } else if (name.includes(cfun[1]) && !defineFailure.includes(cfun[1])) {
                                    testsuitemalus[cfun[1]] = testsuitemalus[cfun[1]] + (resultjson[name] * 1.5)
                                } else if (name.includes(crec[0]) && !defineFailure.includes(crec[0])) {
                                    if (name.includes('OKrec')) {
                                        testsuitemalus[crec[0]] = testsuitemalus[crec[0]] + (resultjson[name] * 5);
                                    } else {
                                        testsuitemalus[crec[0]] = testsuitemalus[crec[0]] + (resultjson[name] * 2)
                                    }

                                } else if (name.includes(crec[1]) && !defineFailure.includes(crec[1])) {
                                    if (name.includes('OKrec')) {
                                        testsuitemalus[crec[1]] = testsuitemalus[crec[1]] + (resultjson[name] * 7);
                                    } else {
                                        testsuitemalus[crec[1]] = testsuitemalus[crec[1]] + (resultjson[name] * 3)
                                    }

                                }

                            }
                        });

                        ntests = ntests + parseInt(xml.testsuite.$.tests);
                        nerrors = nerrors + parseInt(xml.testsuite.$.errors);
                        nskips = nskips + parseInt(xml.testsuite.$.skipped);
                        nfailures = nfailures + parseInt(xml.testsuite.$.failures);


                    });
                    console.log('note:' + note);

                    if (testsuitemalus[cval[0]] > 1) {
                        note = note - 1;
                    } else {
                        note = note - testsuitemalus[cval[0]];
                    }
                    if (testsuitemalus[cval[1]] > 1) {
                        note = note - 1;
                    } else {
                        note = note - testsuitemalus[cval[1]];
                    }
                    if (testsuitemalus[cval[2]] > 1) {
                        note = note - 1;
                    } else {
                        note = note - testsuitemalus[cval[2]];
                    }
                    if (testsuitemalus[cfun[0]] > 3) {
                        note = note - 3;
                    } else {
                        note = note - testsuitemalus[cfun[0]];
                    }
                    if (testsuitemalus[cfun[1]] > 3) {
                        note = note - 3;
                    } else {
                        note = note - testsuitemalus[cfun[1]];
                    }
                    if (testsuitemalus[crec[0]] > 6) {
                        note = note - 6;
                    } else {
                        note = note - testsuitemalus[crec[0]];
                    }
                    if (testsuitemalus[crec[1]] > 9) {
                        note = note - 9;
                    } else {
                        note = note - testsuitemalus[crec[1]];
                    }
                    console.log('testsuitemalus ' + JSON.stringify(testsuitemalus));
                    console.log('note:' + note);
                    note = note / 3;
                    console.log('note:' + note);


                    resultjson.skippedtotal = parseInt(nskips)
                    resultjson.failurestotal = parseInt(nfailures)
                    resultjson.errorstotal = parseInt(nerrors)
                    resultjson.teststotal = parseInt(ntests)
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
                    if (xml.checkstyle.file != null && util.isArray(xml.checkstyle.file)) {
                        xml.checkstyle.file.forEach(function (file1) {
                            if (file1 != null && util.isArray(file1.error)) {
                                file1.error.forEach(function (error1) {
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
                                if (file1 != null && file1.error.$.severity === 'warning') {
                                    resultjson['warn(' + file1.error.$.source + ')'] = resultjson['warn(' + file1.error.$.source + ')'] + 1;
                                    var error1 = file1.error;
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
                        })
                    } else {
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
                            //                        console.log(util.isArray(xml.checkstyle.file.error))
                            //                        console.log(JSON.stringify(xml.checkstyle.file))
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
                    }
                    if (note > 0) {
                        note = note - malus1 - malus2;
                    }
                    if (note < 0) {
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
                    note = precisionRound(note * 4, 0) / 4.0

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