$(function() {
  var editor;
  KindEditor.ready(function(K) {
    editor = K.create('textarea[name="detail"]', {
      width: '100%',
      resizeType : 1,
      allowPreviewEmoticons : false,
      allowImageUpload : false,
      items : [
        'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
        'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
        'insertunorderedlist', '|', 'image']
    });
  });

  $('.image-preview').on('click', function() {
    var $dialog = $('.dialog').modal('show');
    $dialog.find('.modal-title').text('预览');
    $dialog.find('.modal-body').html('<img src="' + $(this).data('src') + '">');
  });

  var $form = $('form');
  var $title = $form.find('input[name="title"]');
  var $abs = $form.find('input[name="abstract"]');
  var $price = $form.find('input[name="price"]');
  var $image = $form.find('input[name="image"]');
  var $detail = $form.find('textarea[name="detail"]');
  var $classify = $form.find('select[name="classify"]');
  var $id = $form.find('input[name="id"]');

  var showError = function(msg) {
    $('#error-text').addClass('has-error').find('.control-label').text(msg);
  };
  var showSuccess = function(msg) {
    $('#error-text').addClass('has-success').find('.control-label').text(msg);
  };
  var clearError = function() {
    $('#error-text').removeClass('has-error').find('.control-label').text('')
  };
  $form.find('.btn').on('click', function() {
    if (!editor) return showError('控件尚未加载完毕，休息会儿吧！');

    $.ajax({
      type: 'post',
      url: '/manage/flower/modify/' + $id.val(),
      data: $form.serialize()
    }).done(function(data) {
      if (data.status !== 1) {
        return showError(data.msg);
      }
      clearError();
      showSuccess('修改成功！');
    });
  });
});