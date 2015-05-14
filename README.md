# JSONA
JSONA -- simplify JSON object Array.

An JSON object array like this:<br>
```javascript
[{"name": "Jone", "age": 12, "scores": {"math": 4, "english": 5}, "height": 150},
 {"name": "Smith", "age": 10, "scores": {"math": 4, "english": 4}, "height": 152}, 
 {"name": "Rose", "age": 11, "scores": {"math": 4, "english": 5}, "height": 148}, 
 ...]
```
In JSONA, it like this:<br>
```javascript
[["@", "name", "age", "scores", ["math", "english"], "height"], 
 ["Jone", 12, [4, 5], 150],
 ["Smith", 10, [4, 4], 152],
 ["Rose", 11, [4, 5], 148],
 ...]
```
There has a field symbol '@' at the first line, it is the first element of the array. That means the array is field name.<br>
So, for the large object array, JSONA can has more little size than JSON.<br>

Grammatically, JSONA is JSON. Especially, it has a field symbol '@', which can customized by user.