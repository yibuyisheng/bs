$(function() {
  var $imgs = $('.img-scroll img');
  $imgs.eq(0).removeClass('hide');

  var changeImg = function() {
    var $curImg = $imgs.filter(':visible');
    var $nextImg = $curImg.next().length ? $curImg.next() : $imgs.eq(0);
    $curImg.fadeOut(function() {
      $curImg.addClass('hide');
      $nextImg.hide().removeClass('hide').fadeIn(function() {
        setTimeout(changeImg, 2000);
      });
    });
  };
  changeImg();
});