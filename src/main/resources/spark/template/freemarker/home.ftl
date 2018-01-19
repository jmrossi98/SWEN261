<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
  
    <h1>Web Checkers</h1>
    
    <div class="navigation">
      <a href="/">my home</a>

      <#if playerName??>
      Welcome, ${playerName}!
      <a href="/signout">Sign Out</a>
      <#else>
      <a href="/signin">Sign In</a>
      <a href="/signinAccount">Sign In With Account</a>
      </#if>
    </div>
    
    <div class="body">
      <p>Welcome to the world of online Checkers.</p>
      <p>There are ${totalPlayers} online right now!</p>


      <form action="./gamestart" method="POST">
          <p></p>
          <#if playerName??>
            <!--
            <p>Games Won: ${WonGames}</p>
            <p>Games Played: ${PlayedGames}</p>
            <p>Average Win Rate: ${WinAverage}</p>
            -->
            <#if invalidOpponentMessage??>
                <p>${invalidOpponentMessage}</p>
            </#if>
            <#if gameWinner??>
                <p></p>
                <p><mark><b><i>${gameWinner}</i></b></mark></p>
                <p><mark><b><i>${winReason}</i></b></mark></p>
                <p></p>
            </#if>
            <p>Players In Lobby:</p>
            <#list lobbyList as player>
                <#if player!=playerName>
                    <p>
                    <button value=${player}
                            type="submit"
                            name="chosenOpponent">${player}
                    </button>
                    </p>
                </#if>
            </#list>
            <p>Players In Games:</p>
            <#list inGameList as player>
                <p>
                    ${player}
                </p>
            </#list>
          </#if>
    </div>
    
  </div>
</body>
</html>
