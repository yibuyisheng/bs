// 新建帖子
$(function() {
  if (window.location.pathname !== '/forum/add') return;

  var editor;
  KindEditor.ready(function(K) {
    editor = K.create('textarea[name="content"]', {
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

  var $form = $('form');
  var $button = $form.find('button');
  var $title = $form.find('input[name="title"]');

  $button.on('click', function() {
    if (!$title.val()) return $('.warn').modal('show').find('.modal-body').html('请输入帖子标题！');
    if (!editor.text()) return $('.warn').modal('show').find('.modal-body').html('请输入内容！');

    $.ajax({
      type: 'post',
      url: '/forum/add',
      data: {title: $title.val(), content: editor.html()}
    }).done(function(data) {
      if (data.status !== 1) return $('.error').modal('show').find('.modal-body').html(data.msg ? data.msg : '未知错误！');
      $('.success').modal('show').find('.modal-body').html('发帖成功！');
    });
  });
});

// 回复帖子
$(function() {
  if (window.location.href.indexOf('/forum/detail') === -1) return;

  var editor;
  KindEditor.ready(function(K) {
    editor = K.create('textarea[name="content"]', {
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

  var $form = $('form');
  var $button = $form.find('button');

  $button.on('click', function() {
    if (!editor.text()) return $('.warn').modal('show').find('.modal-body').html('请输入内容！');

    $.ajax({
      type: 'post',
      url: '/forum/reply/' + $(this).data('fid'),
      data: {content: editor.html()}
    }).done(function(data) {
      if (data.status !== 1) return $('.error').modal('show').find('.modal-body').html(data.msg ? data.msg : '未知错误！');
      window.location.reload();
    });
  });
});