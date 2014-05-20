$(function() {
  var editor;
  KindEditor.ready(function(K) {
    editor = K.create('.reply-box textarea[name="reply"]', {
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

  var $replyBox = $('.reply-box');
  $('.reply').on('click', function() {
    $replyBox.modal();
    $replyBox.find('.content').html($(this).siblings('.content').html());
    editor.html($(this).siblings('.reply').html());

    $replyBox.find('.btn-primary').data('id', $(this).data('id'));
  });
  $replyBox.find('.btn-primary').on('click', function() {
    $.ajax({
      type: 'post',
      url: '/manage/flower-comment/reply/' + $(this).data('id'),
      data: {reply: editor.html()}
    }).done(function(data) {
      if (data.status != 1) return showError(data.msg ? data.msg : '未知错误！');
      window.location.reload();
    });
  });
});