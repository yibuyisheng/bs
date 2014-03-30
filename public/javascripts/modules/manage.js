$(function() {
	var $form = $('#manage');
	var $title = $form.find('input[name="title"]');
	var $abs = $form.find('input[name="abstract"]');
	var $price = $form.find('input[name="price"]');
	var $image = $form.find('input[name="image"]');
	var $detail = $form.find('textarea[name="detail"]');
	var $classify = $form.find('select[name="classify"]');

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
		$.ajax({
			type: 'post',
			url: '/flower/add/ajax',
			data: $form.serialize()
		}).done(function(data) {
			if (data.status !== 1) {
				return showError(data.msg);
			}
			clearError();
			showSuccess('添加成功！');
		});
	});
});