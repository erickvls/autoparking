var regexMask = function(el, mask) {
  var masks = {
      'float-ptbr': /^((\d{1,3}(\.\d{3})*(((\.\d{0,2}))|((\,\d*)?)))|(\d+(\,\d*)?))$/,
      'float-enus': /^((\d{1,3}(\,\d{3})*(((\,\d{0,2}))|((\.\d*)?)))|(\d+(\.\d*)?))$/,
      'time-meridian': /^(([0-1]|0[1-9]|1[0-2])|([0-1]|[0-1][0-9]?)(:(?![A-Za-z]))([0-5]?|[0-5][0-9]?)(?![A-Za-z])( (?![mM]))?([APap])?[mM]?)$/,
      'time': /^(([0-1][0-9]|2[0-3]|[0-2])|([0-1][0-9]|2[0-3]|[0-2]):[0-5]?[0-9]?)$/,
      'integer': /^\d+$/
    },
    eventNs = 'regexmask';
  if (!el) {
    throw 'required target element argument missing';
  } else if (!mask) {
    throw 'required mask argument missing';
  } else if (masks[mask]) {
    mask = masks[mask];
  } else {
    try {
      mask.test("");
    } catch (e) {
      throw 'regexMask requires the test() method';
    }
  }
  $(el).on('focus' + '.' + eventNs, function(event) {
    $(el).on('keypress' + '.' + eventNs, function(event) {
      event.keyCode = event.keyCode ? event.keyCode : event.charCode || event.which;
      if (!event.keyCode) {
        return true;
      }
      var part1 = el.value.substring(0, el.selectionStart),
        part2 = el.value.substring(el.selectionEnd, el.value.length);
      if (!mask.test(part1 + String.fromCharCode(event.charCode) + part2)) {
        event.preventDefault();
        return false;
      }
    });
  }).on('blur' + '.' + eventNs, function() {
    $(el).off(['keypress', 'blur'].join('.' + eventNs + ' ') + '.' + eventNs);
  });
};
regexMask($('[mask="time"]')[0], 'time');