$(function() {
  $('.delete-flower').on('click', function() {
    $.ajax({
      type: 'post',
      url: '/manage/flower/del/' + $(this).data('id')
    }).done(function(data) {
      if (data.status !== 1) {
        return $('.error').modal('show').find('.modal-body').html(data.msg ? data.msg : '未知错误！');
      }
      window.location.reload();
    });
  });
});