$(function() {
  var editorAdd, editorModify;
  KindEditor.ready(function(K) {
    editorAdd = K.create('.add-notice-box textarea[name="notice"]', {
      width: '100%',
      resizeType : 1,
      allowPreviewEmoticons : false,
      allowImageUpload : false,
      items : [
        'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
        'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
        'insertunorderedlist', '|', 'image']
    });

    editorModify = K.create('.modify-notice-box textarea[name="notice"]', {
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

  $('.add-notice').on('click', function() {
    $('.add-notice-box').removeClass('hide').modal();
  });
  $('.add-notice-box .btn-primary').on('click', function() {
    $.ajax({
      type: 'post',
      url: '/manage/notice/add',
      data: {title: $('.add-notice-box input[name="title"]').val(), content: editorAdd.html()}
    }).done(function(data) {
      if (data.status != 1) showError(data.msg ? data.msg : '未知错误！');
      window.location.reload();
    });
  });

  $('.modify').on('click', function() {
    var $modifyBox = $('.modify-notice-box');
    $modifyBox.find('input[name="title"]').val($(this).data('title'));
    editorModify.html($(this).data('content'));
    $modifyBox.find('.btn-primary').data('id', $(this).data('id'));
    $modifyBox.removeClass('hide').modal();
  });

  $('.modify-notice-box .btn-primary').on('click', function() {
    $.ajax({
      type: 'post',
      url: '/manage/notice/modify/' + $(this).data('id'),
      data: {
        title: $('.modify-notice-box input[name="title"]').val(), 
        content: editorModify.html()
      }
    }).done(function(data) {
      if (data.status != 1) showError(data.msg ? data.msg : '未知错误！');
      window.location.reload();
    });
  });

  $('.del').on('click', function() {
    $.ajax({
      type: 'post',
      url: '/manage/notice/del/' + $(this).data('id')
    }).done(function(data) {
      if (data.status != 1) showError(data.msg ? data.msg : '未知错误！');
      window.location.reload();
    });
  });
});