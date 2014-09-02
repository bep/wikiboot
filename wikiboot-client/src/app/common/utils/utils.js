'use strict';

define(['lodash', 'jsonhuman', 'exports'], function (_, JsonHuman, exports) {

    // http://stackoverflow.com/questions/19098797/fastest-way-to-flatten-un-flatten-nested-json-objects
    function flattenJson(data) {
        var result = {};
        function recurse (cur, prop) {
            if (Object(cur) !== cur) {
                result[prop] = cur;
            } else if (Array.isArray(cur)) {
                for(var i=0, l=cur.length; i<l; i++)
                    recurse(cur[i], prop ? prop+"["+i + "]": "["+i + "]");
                if (l == 0)
                    result[prop] = [];
            } else {
                var isEmpty = true;
                for (var p in cur) {
                    isEmpty = false;
                    recurse(cur[p], prop ? prop+"."+p : p);
                }
                if (isEmpty)
                    result[prop] = {};
            }
        }
        recurse(data, "");
        return result;
    }

    function jsonToHtml(json, flatten) {
        return JsonHuman.format(flatten ? flattenJson(json) : json);
    }

    exports.flattenJson = flattenJson;
    exports.jsonToHtml = jsonToHtml;

});
