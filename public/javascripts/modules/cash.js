$(function() {
  var $ordererName = $('*[name="orderer-name"]');
  var $ordererPhone = $('*[name="orderer-phone"]');
  var $ordererMobile = $('*[name="orderer-mobile"]');
  var $ordererProvince = $('*[name="orderer-province"]');
  var $ordererCity = $('*[name="orderer-city"]');
  var $ordererEmail = $('*[name="orderer-email"]');
  var $consigneeName = $('*[name="consignee-name"]');
  var $consigneePhone = $('*[name="consignee-phone"]');
  var $consigneeMobile = $('*[name="consignee-mobile"]');
  var $consigneeProvince = $('*[name="consignee-province"]');
  var $consigneeCity = $('*[name="consignee-city"]');
  var $consigneeAddress = $('*[name="consignee-address"]');
  var $sendArea = $('*[name="send-area"]');
  var $sendDate = $('*[name="send-date"]');
  var $sendTime = $('*[name="send-time"]');
  var $specialNeeds = $('*[name="special-needs"]');
  var $leaveMessage = $('*[name="leave-message"]');
  var $leaveName = $('*[name="leave-name"]');
  var $leavaNameMyName = $('*[name="leave-name-my-name"]');

  var validate = function() {
    if (!$ordererName.val()) return $('.warn').modal('show').find('.modal-body').html('请输入订购人姓名！'), $ordererName.focus(), false;
    if (!$ordererPhone.val()) return $('.warn').modal('show').find('.modal-body').html('请输入订购人电话！'), $ordererPhone.focus(), false;
    if (!$ordererMobile.val()) return $('.warn').modal('show').find('.modal-body').html('请输入订购人手机！'), $ordererMobile.focus(), false;
    if (!$ordererProvince.val()) return $('.warn').modal('show').find('.modal-body').html('请输入订购人省份！'), $ordererProvince.focus(), false;
    if (!$ordererCity.val()) return $('.warn').modal('show').find('.modal-body').html('请输入订购人城市！'), $ordererCity.focus(), false;
    if (!$ordererEmail.val()) return $('.warn').modal('show').find('.modal-body').html('请输入订购人邮件！'), $ordererEmail.focus(), false;
    if (!$consigneeName.val()) return $('.warn').modal('show').find('.modal-body').html('请输入收货人姓名！'), $consigneeName.focus(), false;
    if (!$consigneePhone.val()) return $('.warn').modal('show').find('.modal-body').html('请输入收货人电话！'), $consigneePhone.focus(), false;
    if (!$consigneeMobile.val()) return $('.warn').modal('show').find('.modal-body').html('请输入收货人手机！'), $consigneeMobile.focus(), false;
    if (!$consigneeProvince.val()) return $('.warn').modal('show').find('.modal-body').html('请输入收货人省份！'), $consigneeProvince.focus(), false;
    if (!$consigneeCity.val()) return $('.warn').modal('show').find('.modal-body').html('请输入收货人城市！'), $consigneeCity.focus(), false;
    if (!$consigneeAddress.val()) return $('.warn').modal('show').find('.modal-body').html('请输入收货人地址！'), $consigneeAddress.focus(), false;

    return true;
  };
  var getData = function() {
    var dataStr = [];
    $('form').each(function() {
      dataStr.push($(this).serialize());
    });
    dataStr = dataStr.join('&').replace(/send-date=[0-9|\-]+/, 'send-date=' + new Date($sendDate.val()).getTime());
    return dataStr;
  };
  $('#submit').on('click', function() {
    if (!validate()) return;
    $.ajax({
      type: 'post',
      url: '/order/create',
      data: getData()
    }).done(function(data) {
      if (data.status !== 1) {
        return $('.error').modal('show').find('.modal-body').html(data.msg ? data.msg : '未知错误！');
      }
      $('.success').modal('show').find('.modal-body').html('提交成功！');
    });
  });

  // 确认收货
  $('.confirm-receive').on('click', function() {
    $('.confirm-dialog').modal().find('.modal-body').html('请注意货物确实已经送达，否则货财两空，确定收货吗？');
    $('.confirm-dialog').find('.modal-title').html('提示');

    var id = $(this).data('id');
    $('.confirm-dialog .btn-primary').off('click').on('click', function() {
      $.ajax({
        type: 'post',
        url: '/order/receive/' + id
      }).done(function(data) {
        if (data.status != 1) showError(data.msg ? data.msg : '未知错误！');
        window.location.reload();
      });
    });
  });
});