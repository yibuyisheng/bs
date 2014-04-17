$(function() {
	$('#add-to-cart').on('click', function() {
		var fid = $(this).data('id');
		var count = $(this).data('count');
		$.ajax({
			url: '/cart/add/' + fid + '/' + count,
			type: 'post'
		}).done(function(data) {
			if (data.status != 1) return alert(data.msg ? data.msg : '未知错误！');
			alert('添加成功！');
		});
	});
});