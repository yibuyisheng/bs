$(function() {
  $('a.send').on('click', function() {
    $.ajax({
      url: '/manage/order/send/' + $(this).data('oid'),
      type: 'post'
    }).done(function(data) {
      if (data.status !== 1) {
        return $('.error').modal('show').find('.modal-body').html(data.msg ? data.msg : '未知错误');
      }
      window.location.reload();
    });
  });

  $('a.del').on('click', function() {
    $.ajax({
      url: '/manage/order/del/' + $(this).data('oid'),
      type: 'post'
    }).done(function(data) {
      if (data.status !== 1) {
        return $('.error').modal('show').find('.modal-body').html(data.msg ? data.msg : '未知错误');
      }
      window.location.reload();
    });
  });
});