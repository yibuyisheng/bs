// 注册
$(function() {
  var $form = $('#regist');
  var $nickname = $('input[name="nickname"]');
  var $email = $('input[name="email"]');
  var $truename = $('input[name="truename"]');
  var $password = $('input[name="password"]');
  var $confirmPassword = $('input[name="confirm-password"]');

  var showError = function(msg) {
    $('#error-text').addClass('has-error').find('.control-label').text(msg);
  };
  var clearError = function() {
    $('#error-text').find('.control-label').text('')
  };
  $form.find('button').on('click', function() {
    if (!$nickname.val()) {
      return showError('请输入昵称！');
    }
    if (!$truename.val()) {
      return showError('请输入真实姓名！');
    }
    if (!$password.val()) {
      return showError('请输入密码！');
    }
    if ($password.val() !== $confirmPassword.val()) {
      return showError('两次密码输入不一致！');
    }

    $.ajax({
      url: '/ajax/user/regist',
      type: 'post',
      data: $form.serialize()
    }).done(function(data) {
      if (data.status !== 1) {
        return showError(data.msg);
      } else {
        clearError();
        window.location.assign('/');
      }
    });
  });

});

// 登录
$(function() {
  var $form = $('#login');
  var $account = $form.find('input[name="account"]');
  var $password = $form.find('input[name="password"]');

  var showError = function(msg) {
    $('#error-text').addClass('has-error').find('.control-label').text(msg);
  };
  var clearError = function() {
    $('#error-text').find('.control-label').text('')
  };
  $form.find('button').on('click', function() {
    if (!$account.val()) {
      return showError('请输入账号！');
    }
    if (!$password.val()) {
      return showError('请输入密码！');
    }

    $.ajax({
      url: '/ajax/user/login',
      type: 'post',
      data: $form.serialize()
    }).done(function(data) {
      if (data.status !== 1) {
        return showError(data.msg);
      } else {
        clearError();

        // 跳转
        var url = '/';
        if (window.location.search) {
          var start = window.location.search.indexOf('url=');
          var end = window.location.search.indexOf('&', start);
          end = end === -1 ? window.location.search.length : end;
          var url = window.location.search.slice(start + 4, end);
        }
        window.location.assign(url);
      }
    });
  });
});

// myzone页面
$(function() {
  var $hide = $('.form-group.hide, .row.hide');
  $('.show-modify-password').on('click', function() {
    if ($(this).text() == '修改密码') {
      $hide.removeClass('hide');
      $(this).text('取消修改密码');
    } else {
      $hide.addClass('hide');
      $(this).text('修改密码');
    }
  });

  var $oldPassword = $('form input[name="old-password"]');
  var $password = $('form input[name="password"]');
  var $confirmPassword = $('form input[name="confirm-password"]');
  $('#modify-submit').on('click', function() {
    if (!$oldPassword.val()) return showError('请输入旧密码！');
    else if (!$password.val()) return showError('请输入新密码！');
    else if ($password.val() !== $confirmPassword.val()) return showError('两次密码输入不一致！');
    $.ajax({
      type: 'post',
      url: '/ajax/user/modify',
      data: $('form').serialize()
    }).done(function(data) {
      if (data.status !== 1) return showError(data.msg ? data.msg : '未知错误！');
      window.location.reload();
    });
  });
});