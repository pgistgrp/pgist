function findPosX(obj) {
	var curleft = 0;
	if (obj.offsetParent)	{
		while (obj.offsetParent) {
			curleft += obj.offsetLeft;
			obj = obj.offsetParent;
		}
	} else if (obj.x) {
		curleft += obj.x;
  }
	return curleft;
}

function findPosY(obj) {
	var curtop = 0;
	if (obj.offsetParent)	{
		while (obj.offsetParent) {
			curtop += obj.offsetTop;
			obj = obj.offsetParent;
		}
	} else if (obj.y) {
		curtop += obj.y;
  }
	return curtop;
}

function centerX2Y(x, y) {
  var s = x.style.width;
  if (s.substring(s.length-2)=='px') s = s.substring(0, s.length-2);
  x.style.left = findPosX(y) - s + y.offsetWidth;
}

function toggleDisplay(obj, show, hide) {
  if (obj.style.display!=show) {
    obj.style.display=show;
  } else {
    obj.style.display=hide;
  }
}

//transfer selection from id1 to id2
function transferSelection(id1, id2) {
  obj1 = document.getElementById(id1).options;
  obj2 = document.getElementById(id2).options;
  for (var i=0; i<obj1.length; i++) {
    if (!obj1[i].selected) continue;
    found = false;
    for (var j=0; j<obj2.length; j++) {
      if (obj2[j].value==obj1[i].value) {
        found = true;
        break;
      }
    }
    if (!found) {
      obj2[obj2.length] = new Option(obj1[i].text, obj1[i].value, false, false);
    }
  }
}

function deleteSelection(id) {
  obj = document.getElementById(id).options;
  for (var i=obj.length-1; i>=0; i--) {
    if (obj[i].selected) obj[i] = null;
  }
}

function selection2Hidden(selectionId, hiddenId) {
  selection = document.getElementById(selectionId).options;
  hidden = document.getElementById(hiddenId);
  var s = new Array();
  for (var i=0; i<selection.length; i++) {
    s[s.length]=selection[i].value;
  }
  hidden.value=s.join(',');
}

