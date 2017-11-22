<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="<%=basePath%>">

  <title>Users</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="This is my page">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" />  
  <meta name="apple-mobile-web-app-capable" content="yes" />  
  <meta name="format-detection" content="telephone=no" />
  <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  <link rel="shortcut icon" href="Resource/img/btv.ico">
  <style type="text/css">
    #div_sign_in {margin: 200px auto auto;  width: 400px; height: 350px; background-color: #12263c;
      box-shadow: 0 0 8px #1d4ebb;}
    #div_sign_in_1 {width: 300px; height: 250px; margin: 25px auto auto}
    h3 {width: 100%; text-align: center; align-content: center}
    #error_message_sign_in {color: red; font-size: 16px}
    #btSignIn {width: 100%}

    #div_sign_up {margin: 150px auto auto;  width: 400px; height: 460px; background-color: #12263c;
      box-shadow: 0 0 8px #1d4ebb; display: none}
    #div_sign_up_1 {width: 300px; height: 400px; margin: 25px auto auto}
    #error_message_sign_up {color: red; font-size: 16px}
    #btSignUp {width: 100%}

    #div_reset {margin: 200px auto auto;  width: 400px; height: 320px; background-color: #12263c;
      box-shadow: 0 0 8px #1d4ebb; display: none}
    #div_reset_1 {width: 300px; height: 250px; margin: 25px auto auto}
    #error_message_reset {color: red; font-size: 16px}
    #btReset {width: 100%}

    .aa {color: lightblue; cursor: pointer}
    .aa:hover {color: #1d4ebb}

  </style>
  <script type="application/javascript" src="Resource/js/jquery-3.2.1.js"></script>
  <script type="application/javascript" src="Resource/js/index.js"></script>
</head>

<body style="background-color: #ababab">

  <div id="div_sign_in">
    <div id="div_sign_in_1">
      <br/>
      <h3 style="color: whitesmoke">BLIVE ON BVISION</h3>
      <br/>
      <div>
      <div class="input-group">
        <span class="input-group-addon" id="basic-addon1">
          <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
        </span>
        <input type="text" id="sign_in_username" class="form-control" placeholder="Username" aria-describedby="basic-addon1" name="username">
      </div>
      <br/>
      <div class="input-group">
        <span class="input-group-addon" id="basic-addon2">
          <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
        </span>
        <input type="password" id="sign_in_password" class="form-control" placeholder="Password" aria-describedby="basic-addon2" name="password">
      </div>
      <br/>
      <button id="btSignIn" type="submit" class="btn btn-primary">SignIn</button>
      </div>
      <br/>
      <div>
        <a style="display: block; float: left;" id="a_sign_up" class="aa">SignUp</a>
        <a style="display: block; float: right;" id="a_reset" class="aa">Forgot password?</a>
      </div>
      <br/>
      <div style="color: #b9b9b9; font-size: 12px">
        By signing up the account, you agree to the term of service
        <a href="http://blive.bvision.live:8804/consent.pdf" target="_blank">(consent)</a>
      </div>
      <div style="clear: both"><span id="error_message_sign_in"></span></div>
    </div>
  </div>

  <div id="div_sign_up">
    <span class="glyphicon glyphicon-remove" aria-hidden="true" id="close_sign_up"
          style="float: right; color: whitesmoke; margin: 10px; font-size: 20px; cursor: pointer"></span>
    <div id="div_sign_up_1">
      <br/>
      <h3 style="color: whitesmoke">SignUp</h3>
      <br/>
      <div>
        <div class="input-group">
          <span class="input-group-addon" id="basic-addon3">
            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
          </span>
          <input id="sign_up_username" type="text" class="form-control" placeholder="Username" aria-describedby="basic-addon3" name="username">
        </div>
        <br/>
        <div class="input-group">
          <span class="input-group-addon" id="basic-addon4">
            <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
          </span>
          <input id="sign_up_password" type="password" class="form-control" placeholder="Password" aria-describedby="basic-addon4" name="password">
        </div>
        <br/>
        <div class="input-group">
          <span class="input-group-addon" id="basic-addon5">
            <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
          </span>
          <input id="sign_up_password1" type="password" class="form-control" placeholder="Password" aria-describedby="basic-addon5" name="password1">
        </div>
        <br/>
        <div class="input-group">
          <span class="input-group-addon" id="basic-addon6">
            <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
          </span>
          <input id="sign_up_email" type="email" class="form-control" placeholder="Email" aria-describedby="basic-addon6" name="email">
        </div>
        <br/>
        <div class="input-group">
          <span class="input-group-addon" id="basic-addon7">
            <span class="glyphicon glyphicon-phone" aria-hidden="true"></span>
          </span>
          <input id="sign_up_phone" type="number" class="form-control" placeholder="Cell phone" aria-describedby="basic-addon7" name="phone">
        </div>
        <br/>
        <button id="btSignUp" class="btn btn-primary">SignUp</button>
      </div>
      <br/>
      <span id="error_message_sign_up"></span>
    </div>
  </div>


  <div id="div_reset">
    <span class="glyphicon glyphicon-remove" aria-hidden="true" id="close_reset"
          style="float: right; color: whitesmoke; margin: 10px; font-size: 20px; cursor: pointer"></span>
    <div id="div_reset_1">
      <br/>
      <h3 style="color: whitesmoke">RESET PASSWORD</h3>
      <br/>
      <div>
        <div class="input-group">
          <span class="input-group-addon" id="basic-addon8">
            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
          </span>
          <input type="text" id="reset_username" class="form-control" placeholder="Username" aria-describedby="basic-addon8" name="username">
        </div>
        <br/>
        <div class="input-group">
          <span class="input-group-addon" id="basic-addon9">
            <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
          </span>
          <input type="email" id="reset_email" class="form-control" placeholder="Email" aria-describedby="basic-addon9" name="email">
        </div>
        <br/>
        <button id="btReset" type="submit" class="btn btn-primary">Reset</button>
      </div>
      <br/>
      <div style="clear: both"><span id="error_message_reset"></span></div>
    </div>
  </div>

  <div id="loading" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%;
  background-color: rgba(0,0,0,0.3); z-index: 1000; display: none">
    <div style="width: 25%; margin: 350px auto">
      <div class="progress">
        <div class="progress-bar progress-bar-striped active" role="progressbar"
             aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
        </div>
      </div>
    </div>
  </div>

  <div id="notice" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%;
  background-color: rgba(0,0,0,0.3); z-index: 1001; display: none">
    <div style="width:50%; margin: 100px auto">
      <h4 id="notice_message" style="color: greenyellow; font-size: 20px; width: 100%; text-align: center;"></h4>
    </div>
  </div>
</body>
</html>
