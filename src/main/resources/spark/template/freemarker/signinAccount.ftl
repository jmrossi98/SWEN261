<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="100">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">

    <h1>Web Checkers</h1>

    <div class="navigation">
      <a href="/">my home</a>
    </div>

    <div class="body">
      <p>${title}</p>
      <form action="./signinAccount" method="POST">
        Enter your username.    Enter your password.
        <br/>
        <input name="signinName" />
        <input name="signinPassword" />
        <br/>
        <button type="submit">Ok</button>
      </form>
    </div>

  </div>
</body>
</html>