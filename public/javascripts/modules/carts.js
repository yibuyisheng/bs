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

	var $modifyCountBox = $('.modify-count-box');
	$('.modify-count').on('click', function() {
		$modifyCountBox.modal();
		$modifyCountBox.find('.btn-primary').data('id', $(this).data('id'));

		var price = $(this).data('price');
		var count = $(this).data('count');
		$modifyCountBox.find('input[name="price"]').val(price).data('single-price', price / count);
		$modifyCountBox.find('input[name="count"]').val(count);
	});
	$('.modify-count-box input[name="count"]').on('keyup change', function() {
		var $price = $modifyCountBox.find('input[name="price"]');
		$price.val($price.data('single-price') * $(this).val());
	});
	$('.modify-count-box .btn-primary').on('click', function() {
		var $count = $modifyCountBox.find('input[name="count"]');
		if (!parseInt($count.val()) || parseInt($count.val()) <= 0) {
			return showError('数量必须事正整数！');
		}
		$.ajax({
			type: 'post',
			url: '/cart/modifycount/' + $(this).data('id'),
			data: $('.modify-count-box form').serialize()
		}).done(function(data) {
			if (data.status != 1) showError(data.msg ? data.msg : '未知错误！');
			window.location.reload();
		});
	});
});