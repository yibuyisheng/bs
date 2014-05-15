// 处理thumbnail
$(function() {
  $('.thumbnail>img').on('load', function() {
    debugger
  });
});

function showError(msg) {
  $('.error').modal('show').find('.modal-body').html(msg);
}
function showSuccess(msg) {
  $('.success').modal('show').find('.modal-body').html(msg);
}
function showWarn(msg) {
  $('.warn').modal('show').find('.modal-body').html(msg);
}
