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

