 document.getElementById("loginForm").addEventListener("submit", function (event) {
      event.preventDefault();

      const login = document.getElementById("login").value;
      const password = document.getElementById("password").value;

      const data = { login, password };

      const xhr = new XMLHttpRequest();
      xhr.open("POST", "http://localhost:8080/auth/login", true);
      xhr.setRequestHeader("Content-Type", "application/json");

      xhr.onload = function () {
        if (xhr.status === 200) {
          const token = xhr.responseText;
          localStorage.setItem("token", token);
          alert("Login realizado com sucesso!");

          // Verifica se o login é de admin
          const decodedToken = JSON.parse(atob(token.split('.')[1]));
          if (decodedToken.role === 'ADMIN') {
            window.location.href = "admin.html";
          } else {
            window.location.href = "home.html";
          }
        } else {
          alert("Erro no login: " + xhr.responseText);
        }
      };

      xhr.onerror = function () {
        alert("Erro de rede ou CORS.");
      };

      xhr.send(JSON.stringify(data));
    });