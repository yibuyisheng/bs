$(function() {
	$('#add-to-cart').on('click', function() {
		var fid = $(this).data('id');
		var count = $(this).data('count');
		$.ajax({
			url: '/cart/add/' + fid + '/' + count,
			type: 'post'
		}).done(function(data) {
			if (data.status != 1) {
				return $('.error').modal('show').find('.modal-body').html(data.msg ? data.msg : '未知错误！');
			}
			$('.success').modal('show').find('.modal-body').html('添加成功！');
		});
	});


	var editor;
  KindEditor.ready(function(K) {
    editor = K.create('.comment-box textarea[name="comment"]', {
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
	$('#comment').on('click', function() {
		$('.comment-box').removeClass('hide').modal();
	});
	$('.comment-box .btn-primary').on('click', function() {
		if (!editor.text()) return showError('请输入评价内容！');
		$.ajax({
			type: 'post',
			url: '/flower/comment/' + $(this).data('id'),
			data: {content: editor.html()}
		}).done(function(data) {
			if (data.status !== 1) return showError(data.msg ? data.msg : '未知错误！');
			window.location.reload();
		});
	});
});