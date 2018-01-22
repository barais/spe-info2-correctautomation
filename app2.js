var jsonQuery = require('json-query')
var si2 = require('./SI2.json');
const xml2js = require('xml2js');
var fs = require('fs');
var path = require('path');
var util = require('util');
function people(key/*17011558 */){
    /*
  people.NUMERO;
  people.NOM;
  people.EMAIL;
  people.GROUPE;
*/
    return jsonQuery('[NUMERO='+key+']', {
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
  
// awk -F"base64," '{print $NF}' CP1_11011169.zip | base64 --decode > test.zip


var data = fs.readFileSync(path.join('/tmp/tmp-17116eEJ7N27MloHG/scalastyle-output.xml'));
var xml = parseSync(data);
console.log(util.isArray(xml.checkstyle.file.error));