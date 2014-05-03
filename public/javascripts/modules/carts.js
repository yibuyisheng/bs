$(function() {
	$('.del').on('click', function() {
		var cid = $(this).data('cid');
		$.ajax({
			type: 'post',
			url: '/cart/del/' + cid
		}).done(function(data) {
			if (data.status != 1) {
				return $('.error').model('show').find('.modal-body').html(data.msg ? data.msg : '未知错误！');
			}
			window.location.reload();
		});
	});
});