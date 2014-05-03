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
});