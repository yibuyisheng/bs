$(function() {
  $('.add-user').on('click', function() {
    $('.add-user-box').modal();
  });
  $('.add-user-box .btn-primary').on('click', function() {
    $.ajax({
      type: 'post',
      url: '/ajax/user/regist',
      data: $('.add-user-box form').serialize()
    }).done(function(data) {
      if (data.status != 1) return showError(data.msg ? data.msg : '未知错误！');
      window.location.reload();
    });
  });

  $('.del-user').on('click', function() {
    $('.del-user-box').modal();
    $('.del-user-box .btn-primary').data('id', $(this).data('id'));
  });
  $('.del-user-box .btn-primary').on('click', function() {
    $.ajax({
      type: 'post',
      url: '/manage/user/del/' + $(this).data('id')
    }).done(function(data) {
      if (data.status != 1) return showError(data.msg ? data.msg : '未知错误！');
      window.location.reload();
    });
  });
});