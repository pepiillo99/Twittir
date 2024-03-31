if (getCookie("login") != null) {
    fetchAsync("http://localhost:8080/api/login", "POST").then(function(data) {
        if (data.ok) {
            console.log("sesion iniciada!")
        } else {
            console.log("sesion no iniciada")
            deleteCookie("login")
        }
    }).catch(function(reason) {
        console.log("Error: " + reason)
    })
}
function go(route, needLogin) {
    if (location.href != location.origin + "/" + route) {
        if (needLogin) {
            if (getCookie("login") != null) {
                location.search = ""
                location.pathname = route
            } else {
                makeLoginOrRegister()
            }
        } else {
            location.search = ""
            location.pathname = route
        }
    }
}
function postImage() {
    let photo = document.getElementById("image-file").files[0];
    let formData = new FormData();
    formData.append("file", photo);
    console.log("imagen subiendo! " + formData)
    fetchAsync("http://localhost:8080/api/uploadImageProfile?user=1", "POST", formData).then(function(data) {
        console.log("imagen actualizada correctamente")
    }).catch(function(reason) {
        console.log(reason)
    })
}
function postButton() {
    var tweetContent = document.getElementById("tweetInput").value
    console.log("intentando postear " + tweetContent)
    fetchAsync("http://localhost:8080/api/postTweet?content=" + tweetContent, "POST").then(function(data) {
        addTweet(data, "tweetsContainer")
        var tweetInput = document.getElementById("tweetInput")
        tweetInput.value = ""
        tweetInput.style.height = "1.5rem"
    }).catch(function(reason) {
        console.log(reason)
    })
}
function addTweet(tweetData, container) {
    var containerElement = document.getElementById(container)
    if (containerElement != null) {
        var tweetElement = document.createElement("div")
        tweetElement.classList.add("tweet")

        var tweetUserDataElement = document.createElement("div")
        tweetUserDataElement.classList.add("tweetUserData")

        var tweetUserElement = document.createElement("div")
        tweetUserElement.style.cursor = "pointer"
        tweetUserElement.classList.add("tweetUserData")
        tweetUserElement.onclick = function() {
            go('profile/' + tweetData.userName, false)
        }
        tweetUserDataElement.appendChild(tweetUserElement)

        var userImageElement = document.createElement("img")
        userImageElement.classList.add("userImage")
        userImageElement.onerror = function() {
            this.src='http://localhost:8080/images/default_user.png'
        }
        userImageElement.src = "http://localhost:8080/api/getUserImageProfile?user=" + tweetData.userID
        tweetUserElement.appendChild(userImageElement)

        var nameElement = document.createElement("p")
        nameElement.style.fontWeight = "bold"
        nameElement.textContent = tweetData.name
        tweetUserElement.appendChild(nameElement)

        if (tweetData.userVerified) {
            var verifiedElement = document.createElement("img")
            verifiedElement.classList.add("verified")
            verifiedElement.src = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAACXBIWXMAAAsTAAALEwEAmpwYAAACo0lEQVR4nO2ZO2/UQBSFHUA0lAR5zpllf8AqQgghIRCPFJSIl/gDlIhQRfwHioAWKHgo2pYuoQvQ0SBRw0IgTQBBUlEmFRp0tV5ltdjZ8dhjzy650pG2sGfut/bI594bRXvxH4TWehZAB8AagO1E8rujlLoQhR4AmiTfkDQj9Jrk0SjE0FofI7lpAdHXRhzHM1FIoZQ6AuBHDoi+vgGYriPnKaXUSZJ3SbYB3AcwR3LZAaKvJVlD1iLZlrVlD9nLCwGAayQ/FUg4r7oArpTJcIDk4woBzJAeSQ6FKUg+rxHCiAA8LQShtb5RNwR3YK66cuwD8CUgkPdOFCTP1J08/9Xx3CAA7gSQuBnSLReQhQASN0O65wLyIIDEzaDkz52UV+t2bu9E8mWFCf4B8NviumVrbyYu1tEAOkOQvKmUatm4ZwDfR7rmpJ7YrBpC9o7j+BCAd5b3bWitG5kglkWRFwiSb3Pe/yrrlZodIwgjUkqdT3utOuMEwZ4W00DWxgzCiBdMA9l2WOgFyc91QLC3/1YZICtRFO1vNpuwgCkdgruAfM27CMmLcu8IGC8Q7OWwWspht4DxBsGswy4dQNfHmwHjG8IAOJv6LUk6gGXAdH1DsHdG00PamPL5LwrTarUOen4SPxuNhs4ESTafkQ5gURiPT2JdDOauEAMHf9q1eygw0ljzBLFE8rAVxEQVVpNW6i7UnThLaj7MBZC4KdwOInk6gMTNoLTWp1xbpqt1J88ddZ1nJkqp6wEAGFHhWQnJZwFAPIlKGvQ8rBGkXcqgpx8kL5P8WCHAB5KXIk8xBeAEyXmPw9B52cPbMNTCm7kYzfXc3sl3JK7ZugQA8MvaxVYd0saUDqAFyMrIeiKEAHBO6mnpOyW2fiuZRy5mlqd7MWHxF4ctfYD7t/0KAAAAAElFTkSuQmCC";
            tweetUserElement.appendChild(verifiedElement)
        }

        var userNameElement = document.createElement("p")
        userNameElement.style.color = "gray"
        userNameElement.style.margin = "0 0 0 5px"
        userNameElement.style.fontSize = "0.8rem"
        userNameElement.textContent = "@" + tweetData.userName
        tweetUserElement.appendChild(userNameElement)

        var timeElement = document.createElement("p")
        timeElement.style.color = "gray"
        timeElement.style.fontSize = "0.8rem"
        timeElement.style.margin = "0 0 0 5px"
        timeElement.textContent = "· " + timeConversion(tweetData.serverTime - tweetData.postTime, tweetData.serverTime)
        tweetUserDataElement.appendChild(timeElement)

        var tweetContentElement = document.createElement("div")
        tweetContentElement.classList.add("tweetContent")

        var contentParagraph = document.createElement("p")
        contentParagraph.textContent = tweetData.content
        
        tweetContentElement.appendChild(contentParagraph)
        
        if (tweetData.image != null) {
            var contentImageElement = document.createElement("div")
            contentImageElement.classList.add("tweetContentImage")
            var contentImage = document.createElement("img")
            contentImage.src = "https://avatars.akamai.steamstatic.com/09777f00fd3804354dc8afcd68c91f417c70536a_full.jpg"
            contentImageElement.appendChild(contentImage)
            tweetContentElement.appendChild(contentImageElement)
        }

        var tweetActionsElement = document.createElement("div")
        tweetActionsElement.classList.add("tweetActions")

        var divReply = document.createElement("div")
        var replyIcon = document.createElement("i")
        replyIcon.classList.add("fa-solid", "fa-reply", "reply", "clickable")
        divReply.appendChild(replyIcon)
        
        var divReweet = document.createElement("div")
        var retweetIcon = document.createElement("i")
        retweetIcon.style = 'transition-duration: 0.8s; transition-property: transform;'
        retweetIcon.classList.add("fa-solid", "fa-retweet", "clickable")
        if (tweetData.shared) {
            retweetIcon.classList.add("retweeted")
        } else {
            retweetIcon.classList.add("retweet")
        }
        var retweetsCount = document.createElement("p")
        retweetsCount.addEventListener("click", function() {
            console.log("retweet count")
        })
        retweetIcon.addEventListener("click", function() {
            fetchAsync("http://localhost:8080/api/" + (tweetData.shared ? "un" : "") + "shareTweet?tweetID=" + tweetData.id, "POST").then(function(data) {
                if (data.ok) {
                    if (data.update) {
                        tweetData.shared = !tweetData.shared
                        if (data.retweets != 0) {
                            retweetsCount.innerText = data.retweets
                        } else {
                            retweetsCount.innerText = ''
                        }
                        retweetIcon.classList.remove("retweet")
                        retweetIcon.classList.remove("retweeted")
                        if (tweetData.shared) {
                            retweetIcon.style.transform = 'rotate(360deg)'
                            retweetIcon.classList.add("retweeted")
                            
                        } else {
                            retweetIcon.classList.add("retweet")
                            retweetIcon.style.transform = 'rotate(0deg)'
                        }
                    }
                } else {
                    if (data.error.status == 401) {
                        makeLoginOrRegister()
                    } else {
                        console.log(data.error)
                    }
                }
            }).catch(function(error) {
                console.log(error)
            })
        })
        retweetsCount.style.margin = 0
        retweetsCount.classList = [ "clickable" ]
        if (tweetData.retweets != 0) {
            retweetsCount.innerText = tweetData.retweets   
        }
        divReweet.appendChild(retweetIcon)
        divReweet.appendChild(retweetsCount)
        
        
        var divLike = document.createElement("div")
        var likeIcon = document.createElement("i")
        likeIcon.classList.add("fa-heart", "clickable")
        if (tweetData.liked) {
            likeIcon.classList.add("liked")
            likeIcon.classList.add("fa-solid")
        } else {
            likeIcon.classList.add("like")
            likeIcon.classList.add("fa-regular")
        }
        var likeCount = document.createElement("p")
        likeCount.addEventListener("click", function() {
            console.log("like count")
        })
        likeIcon.addEventListener("click", function() {
            fetchAsync("http://localhost:8080/api/" + (tweetData.liked ? "un" : "") + "likeTweet?tweetID=" + tweetData.id, "POST").then(function(data) {
                if (data.ok) {
                    if (data.update) {
                        tweetData.liked = !tweetData.liked
                        if (data.likes != 0) {
                            likeCount.innerText = data.likes
                        } else {
                            likeCount.innerText = ''
                        }
                        likeIcon.classList.remove("like")
                        likeIcon.classList.remove("fa-regular")
                        likeIcon.classList.remove("liked")
                        likeIcon.classList.remove("fa-solid")
                        if (tweetData.liked) {
                            likeIcon.classList.add("liked")
                            likeIcon.classList.add("fa-solid")
                            animateCSS(likeIcon, "animate__heartBeat")
                        } else {
                            likeIcon.classList.add("like")
                            likeIcon.classList.add("fa-regular")
                        }
                    }
                } else {
                    if (data.error.status == 401) {
                        makeLoginOrRegister()
                    } else {
                        console.log(data.error)
                    }
                }
            }).catch(function(error) {
                console.log(error)
            })
        })
        likeCount.style.margin = 0
        likeCount.classList = [ "clickable" ]
        if (tweetData.likes != 0) {
            likeCount.innerText = tweetData.likes   
        }
        divLike.appendChild(likeIcon)
        divLike.appendChild(likeCount)

        tweetActionsElement.appendChild(divReply)
        tweetActionsElement.appendChild(divReweet)
        tweetActionsElement.appendChild(divLike)

        tweetElement.appendChild(tweetUserDataElement)
        tweetElement.appendChild(tweetContentElement)
        tweetElement.appendChild(tweetActionsElement)

        containerElement.insertBefore(tweetElement, containerElement.firstChild)
    }
}
function followAction(userData, button) {
    console.log(userData)
    if (userData != null) {
        fetchAsync("http://localhost:8080/api/" + (isFollow ? "un" : "") + "followUser?followID=" + profileData.id, "POST").then(function(data) {
            if (data.ok) {
                if (data.update) {
                    let followingCounter = document.getElementById("followingCounter");
                    let followersCounter = document.getElementById("followersCounter");
                    if (followingCounter != null && followersCounter != null) {
                        followingCounter.innerHTML = data.counter.following
                        followersCounter.innerHTML = data.counter.followers
                    }
                    if (data.counter.follow) {
                        isFollow = true
                        followButtonSetStyleFollowing(button)
                    } else {
                        isFollow = false
                        button.innerHTML = "Seguir"
                        button.style = null
                    }
                }
            } else {
                console.log(data)
            }
        }).catch(function(reason) {
            console.log("Error: " + reason)
        })
    } else {
        makeLoginOrRegister();
    }
}
function followButtonHover(button, hover) {
    if (isFollow) {
        if (hover) {
            button.innerHTML = "Dejar de seguir"
            button.style.color = "#dc3545"
            button.style.background = "#ffdcdc"
            button.style.border = "#dc354550"
            button.style.borderStyle = "solid"
            button.style.borderWidth = "1px"
        } else {
            followButtonSetStyleFollowing(button)
        }
    }
}
function followButtonSetStyleFollowing(button) {
    button.innerHTML = "Siguiendo"
    button.style.color = "#212529"
    button.style.background = "#ffffff"
    button.style.border = "#21252950"
    button.style.borderStyle = "solid"
    button.style.borderWidth = "1px"
}
function hasScrollbar(elem_id) {
    const elem = document.getElementById(elem_id);
    return elem.clientHeight < elem.scrollHeight
}
const currentDate = new Date()
function timeConversion(restantTime, realTime) {
    var result = ""
    var sInMilli = 1000
    var mInMilli = sInMilli * 60
    var hInMilli = mInMilli * 60
    var dInMilli = hInMilli * 24
    var semInMilli = dInMilli * 7
    if (restantTime > semInMilli * 4) { // mas de un mes
        var date = new Date(realTime)
        var options = {month: 'long'};
        var month = date.toLocaleDateString(getLang(), options).substring(0, 3) // coge las primeras tres letras del mes con el lenguaje del navegador
        month = month.charAt(0).toUpperCase() + month.substring(1, 3) // pone la primera en mayus
        result = date.getDate() + " " + month + "." + (currentDate.getFullYear() != date.getFullYear() ? " " + date.getFullYear() : "")
    } else if (restantTime > semInMilli) {
        result = parseInt(restantTime / semInMilli) + "sem"
    } else if (restantTime > dInMilli) {
        result = parseInt(restantTime / dInMilli) + "d"
    } else if (restantTime > hInMilli) {
        result = parseInt(restantTime / hInMilli) + "h"
    } else if (restantTime > mInMilli) {
        result = parseInt(restantTime / mInMilli) + "m"
    } else if (restantTime < mInMilli) {
        result = parseInt(restantTime / sInMilli) + "s"
    }
    return result
}
function getLang() {
    if (navigator.languages != undefined) {
        return navigator.languages[0];
    }
    return navigator.language;
}
async function fetchAsync(url, method, body) {
    let headers = {};
    let loginCookie = getCookie("login");
    if (loginCookie) {
        headers['Authorization'] = 'Bearer ' + loginCookie;
    }
    let options = {
        method: method,
        headers: headers
    };
    if (method !== 'GET') {
        if (body instanceof FormData) {
            options.body = body;
        } else {
            /*
                headers['Content-Type'] = 'application/json';
                options.body = JSON.stringify(body);
            */
            headers['Content-Type'] = 'application/x-www-form-urlencoded';
            options.body = body;
        }
    }
    let response = await fetch(url, options);
    let data = await response.json();
    if (response.ok) {
        if (method === "POST") {
            data.ok = true;
        }
        return data;
    } else {
        return { error: { status: response.status, statusText: data } };
    }
}
function setCookie(name,value,days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days*24*60*60*1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}
function deleteCookie(name) {
  document.cookie = name +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}
function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}
function logout() {
    deleteCookie("login")
    location.reload()
}
function loading(message) {
    Swal.fire({
        title: message,
        html: 'Por favor, espera...',
        allowEscapeKey: false,
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading()
        }
    });    
}
function makeLoginOrRegister(user, pass) {
    Swal.fire({
        title: 'Inicio de sesión',
        html: `
            <input type="text" id="username" class="swal2-input" placeholder="Usuario">
            <input type="password" id="password" class="swal2-input" placeholder="Contraseña">`,
        confirmButtonText: 'Iniciar sesión',
        focusConfirm: false,
        showLoaderOnConfirm: true,
        didOpen: () => {
            const popup = Swal.getPopup()
            usernameInput = popup.querySelector('#username')
            passwordInput = popup.querySelector('#password')
            if (user) {
                usernameInput.value = user
            }
            if (pass) {
                passwordInput.value = pass
            }
            usernameInput.onkeyup = (event) => event.key === 'Enter' && Swal.clickConfirm()
            passwordInput.onkeyup = (event) => event.key === 'Enter' && Swal.clickConfirm()
        },
        preConfirm: () => {
            const username = usernameInput.value
            const password = passwordInput.value
            if (!username || !password) {
                if (!username) {
                    animateCSS(usernameInput, "animate__shakeX")
                }
                if (!password) {
                    animateCSS(passwordInput, "animate__shakeX")
                }
                Swal.showValidationMessage(`Por favor introduce el usuario y contraseña`)
                return false
            } else {
                loading("Iniciando sesión...")
                fetchAsync("http://localhost:8080/api/login", "POST", JSON.stringify({username: username, password: password})).then(function(data) {
                    if (data.ok) {
                        setCookie("login", data.loginToken)
                        Swal.fire({
                            title: "Sesión iniciada",
                            icon: "success",
                            confirmButtonText: 'Vale'
                        }).then(function() {
                            location.reload()
                        });
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Oops...",
                            text: "Usuario o contraseña incorrectos",
                            confirmButtonText: 'Intentar denuevo',
                            footer: '<a href="#">¿Problemas para iniciar sesión?</a>'
                        }).then(function() {
                            makeLoginOrRegister(username, password, true)
                        });
                        console.log(data.error)
                    }
                }).catch(function(reason) {
                    console.log(reason)
                });
                return true
            }
        },
        footer: '<u onclick="makeRegister()" style="color: rgba(var(--bs-link-color-rgb), var(--bs-link-opacity, 1));cursor: pointer;">¿No estás registrado?</u>'
    })
}
function makeRegister() {
    Swal.fire({
        title: 'Registrarse',
        html: `
            <input type="text" id="name" class="swal2-input" placeholder="Nombre">
            <input type="text" id="username" class="swal2-input" placeholder="Usuario">
            <input type="password" id="password" class="swal2-input" placeholder="Contraseña">
            <input type="password" id="confirmpassword" class="swal2-input" placeholder="Confirmar contraseña">`,
        confirmButtonText: 'Registrarse',
        focusConfirm: false,
        didOpen: () => {
            const popup = Swal.getPopup()
            nameInput = popup.querySelector('#name')
            usernameInput = popup.querySelector('#username')
            passwordInput = popup.querySelector('#password')
            confirmPasswordInput = popup.querySelector('#confirmpassword')
            nameInput.onkeyup = (event) => event.key === 'Enter' && Swal.clickConfirm()
            confirmPasswordInput.onkeyup = (event) => event.key === 'Enter' && Swal.clickConfirm()
            usernameInput.onkeyup = (event) => event.key === 'Enter' && Swal.clickConfirm()
            passwordInput.onkeyup = (event) => event.key === 'Enter' && Swal.clickConfirm()
        },
        preConfirm: () => {
            const name = nameInput.value
            const username = usernameInput.value
            const password = passwordInput.value
            const confirmPassword = confirmPasswordInput.value
            if (!username || !password || !name || !confirmPassword) {
                if (!username) {
                    animateCSS(usernameInput, "animate__shakeX")
                }
                if (!name) {
                    animateCSS(nameInput, "animate__shakeX")
                }
                if (!password) {
                    animateCSS(passwordInput, "animate__shakeX")
                }
                if (!confirmPassword) {
                    animateCSS(confirmPasswordInput, "animate__shakeX")
                }
                Swal.showValidationMessage(`Por favor completa todos los campos`)
            } else if (password == confirmPassword) {                
                loading("Registrando...")
                fetchAsync("http://localhost:8080/api/register", "POST", JSON.stringify({name: name, username: username, password: password})).then(function(data) {
                    if (data.ok) {
                        setCookie("login", data.loginToken)
                        Swal.fire({
                            title: "Registrado correctamente",
                            icon: "success",
                            confirmButtonText: 'Vale'
                        }).then(function() {
                            location.reload()
                        });
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Oops...",
                            text: "No se ha podido registrar el usaurio",
                            confirmButtonText: 'Intentar denuevo',
                            footer: '<a href="#">¿Problemas para registrar?</a>'
                        }).then(function() {
                            makeRegister(true)
                        });
                        console.log(data.error)
                    }
                }).catch(function(reason) {
                    console.log(reason)
                });
            } else {
                Swal.showValidationMessage(`Las contraseñas no coinciden`)
            }
            return { username, password }
        },
        footer: '<u onclick="makeLoginOrRegister()" style="color: rgba(var(--bs-link-color-rgb), var(--bs-link-opacity, 1));cursor: pointer;">Ya estoy registrado</u>'
    })
}
function showImageProfile(userid) {
    Swal.fire({
        html: `<img src="http://localhost:8080/api/getUserImageProfile?user=` + userid + `" style="height: 70vh;width: 70vh;border-radius: 360px;" onerror="this.src='http://localhost:8080/images/default_user.png';">`,
        color: "#716add",
        showConfirmButton: false,
        showCloseButton: true,
        background: "#transparent",
        backdrop: `rgba(0,0,0,0.6)`,
        didOpen: () => {
            const mainDiv = document.getElementsByClassName("swal2-modal")[0]
            mainDiv.style = "display: grid;height: 90vh;width: 90vh;color: transparent;background: transparent;"
            console.log(mainDiv)
        }
    });
}
function showFollowList(followid, following) { // following ? "siguiendo" : "seguidores"
    Swal.fire({
        html: `<div id="followList"></div>`,
        showConfirmButton: false,
        showCloseButton: true,
        didOpen: () => {
            const mainDiv = document.getElementById("followList")
            fetchAsync("http://localhost:8080/api/" + (following ? "getFollowing" : "getFollowers") +"?user=" + followid, "GET").then(function(data) {
                for (userdata in data) {
                    console.log(data[userdata])
                    mainDiv.appendChild(createFollowDiv(data[userdata]))
                }
            }).catch(function(reason) {
                console.log("Error: " + reason)
            })
        }
    });
}
function createFollowDiv(userData) {
    const followDiv = document.createElement("div")
    followDiv.style = "display: flex;flex-direction: row;align-items: center;"

    const profileImage = document.createElement("img")
    profileImage.src = "http://localhost:8080/api/getUserImageProfile?user=" + userData.id
    profileImage.onerror = function () {
        this.src= 'http://localhost:8080/images/default_user.png';
    }
    profileImage.style = "height: 4rem;width: 4rem;border-radius: 360px;margin: 0 1rem 0 0;"

    const div1 = document.createElement("div")
    div1.style = "display: flex;flex-direction: column;align-items: flex-start;"
    div1.classList = "clickable"
    div1.onclick = function() {
        go('profile/' + userData.userName)
    }
    const div2 = document.createElement("div")
    div2.style = "display: flex;flex-direction: row;align-items: center;"
    const name = document.createElement("h1")
    name.style = "margin: 0;"
    name.innerHTML = userData.name
    div2.appendChild(name)
    if (userData.verified) {
        const verified = document.createElement("img")
        verified.classList = "verified"
        verified.src = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAACXBIWXMAAAsTAAALEwEAmpwYAAACo0lEQVR4nO2ZO2/UQBSFHUA0lAR5zpllf8AqQgghIRCPFJSIl/gDlIhQRfwHioAWKHgo2pYuoQvQ0SBRw0IgTQBBUlEmFRp0tV5ltdjZ8dhjzy650pG2sGfut/bI594bRXvxH4TWehZAB8AagO1E8rujlLoQhR4AmiTfkDQj9Jrk0SjE0FofI7lpAdHXRhzHM1FIoZQ6AuBHDoi+vgGYriPnKaXUSZJ3SbYB3AcwR3LZAaKvJVlD1iLZlrVlD9nLCwGAayQ/FUg4r7oArpTJcIDk4woBzJAeSQ6FKUg+rxHCiAA8LQShtb5RNwR3YK66cuwD8CUgkPdOFCTP1J08/9Xx3CAA7gSQuBnSLReQhQASN0O65wLyIIDEzaDkz52UV+t2bu9E8mWFCf4B8NviumVrbyYu1tEAOkOQvKmUatm4ZwDfR7rmpJ7YrBpC9o7j+BCAd5b3bWitG5kglkWRFwiSb3Pe/yrrlZodIwgjUkqdT3utOuMEwZ4W00DWxgzCiBdMA9l2WOgFyc91QLC3/1YZICtRFO1vNpuwgCkdgruAfM27CMmLcu8IGC8Q7OWwWspht4DxBsGswy4dQNfHmwHjG8IAOJv6LUk6gGXAdH1DsHdG00PamPL5LwrTarUOen4SPxuNhs4ESTafkQ5gURiPT2JdDOauEAMHf9q1eygw0ljzBLFE8rAVxEQVVpNW6i7UnThLaj7MBZC4KdwOInk6gMTNoLTWp1xbpqt1J88ddZ1nJkqp6wEAGFHhWQnJZwFAPIlKGvQ8rBGkXcqgpx8kL5P8WCHAB5KXIk8xBeAEyXmPw9B52cPbMNTCm7kYzfXc3sl3JK7ZugQA8MvaxVYd0saUDqAFyMrIeiKEAHBO6mnpOyW2fiuZRy5mlqd7MWHxF4ctfYD7t/0KAAAAAElFTkSuQmCC"
        div2.appendChild(verified)
    }
    div1.appendChild(div2)
    const username = document.createElement("p")
    username.style = "color: gray;margin: 0;"
    username.innerHTML = "@" + userData.userName
    div1.appendChild(username)
    followDiv.appendChild(profileImage)
    followDiv.appendChild(div1)

    return followDiv
}
function editProfileAction(name, userID, profileAboutData) {
    Swal.fire({
        title: 'Editar perfil',
        html: `
            <div style="display: flex;flex-direction: row;align-items: center;justify-content: center;">
                <input id="imageProfile-Input" type="file" onchange="document.getElementById('imageProfileToChange').src = window.URL.createObjectURL(this.files[0])" hidden/>
                <img id="imageProfileToChange" src="http://localhost:8080/api/getUserImageProfile?user=` + userID + `"  style="height: 5rem;width: 5rem;border-radius: 360px;" onerror="this.src='http://localhost:8080/images/default_user.png';">
                <i class="fa-regular fa-image changeImage clickable" aria-hidden="true" onclick="document.getElementById('imageProfile-Input').click()"></i> 
            </div>
            <input type="text" id="name" class="swal2-input" placeholder="Nombre">
            <textarea type="text" id="desc" class="swal2-input" placeholder="Descripción"></textarea>
            <input type="text" id="ubi" class="swal2-input" placeholder="Ubicación">
            <div>
                <a>Fecha nacimiento</a>
                <input type="date" id="birthdate" class="swal2-input" placeholder="Cumpleaños" style="margin: 1rem 1rem 0 0.5rem;">
            </div>
            <input type="text" id="work" class="swal2-input" placeholder="Trabajo">
            <input type="email" id="email" class="swal2-input" placeholder="Correo">`,
        confirmButtonText: 'Guardar',
        showCloseButton: true,
        focusConfirm: false,
        didOpen: () => {
            const popup = Swal.getPopup()
            imageProfileInput = popup.querySelector('#imageProfile-Input')
            ubiInput = popup.querySelector('#ubi')
            ubiInput.value = profileAboutData.location
            birthdateInput = popup.querySelector('#birthdate')
            if (profileAboutData.birthDate != 0) {
                const birthdateDate = new Date(profileAboutData.birthDate)
                birthdateInput.value = birthdateDate.toISOString().slice(0, 10);
            }
            workInput = popup.querySelector('#work')
            workInput.value = profileAboutData.work
            emailInput = popup.querySelector('#email')
            emailInput.value = profileAboutData.email
            nameInput = popup.querySelector('#name')
            nameInput.value = name
            descTextArea = popup.querySelector('#desc')
            descTextArea.value = profileAboutData.description
            descTextArea.setAttribute("style", "height:" + (descTextArea.scrollHeight) + "px;overflow-y:hidden;resize: none;outline-width: 0;margin: 1rem 0 0 0;width: 16rem;");
            descTextArea.style.height = (descTextArea.scrollHeight) + "px";
            descTextArea.oninput = function() {
                descTextArea.style.height = 'auto';
                descTextArea.style.height = (descTextArea.scrollHeight) + "px";
            }
        },
        preConfirm: () => {
            const name = nameInput.value
            const ubi = ubiInput.value
            const birthdate = birthdateInput.value
            const work = workInput.value
            const email = emailInput.value
            const desc = descTextArea.value
            const phoneNumber = 0
            const numberPrefix = 0
            console.log(birthdate)
            if (!name) {
                if (!name) {
                    animateCSS(nameInput, "animate__shakeX")
                }
                Swal.showValidationMessage(`El nombre no puede estar vacío`)
            } else {
                loading("Actualizando...")
                let image = imageProfileInput.files[0];
                if (image != null) {
                    let formData = new FormData();
                    formData.append("file", image);
                    console.log("imagen subiendo! " + formData)
                    fetchAsync("http://localhost:8080/api/uploadImageProfile", "POST", formData).then(function(data) {
                        console.log("imagen actualizada correctamente")
                    }).catch(function(reason) {
                        console.log(reason)
                    })
                }
                fetchAsync("http://localhost:8080/api/updateUserAboutData?newname=" + name + "&location=" + ubi + "&birthdate=" + (birthdate.length  ? new Date(birthdate).getTime() : 0) + "&work=" + work + "&email=" + email + "&description=" + desc + "&numberPrefix=" + numberPrefix + "&phoneNumber=" + phoneNumber, "POST").then(function(data) {
                    if (data.ok) {
                        Swal.fire({
                            title: "Datos actualizados",
                            icon: "success",
                            confirmButtonText: 'Vale'
                        }).then(function() {
                            location.reload()
                        });
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Oops...",
                            text: "No se ha podido acctualizar los datos",
                            confirmButtonText: 'Volver'
                        }).then(function() {});
                        console.log(data.error)
                    }
                }).catch(function(reason) {
                    console.log(reason)
                });
            }
            return {}
        },
    })
}
function animateCSS(element, animation) {
    element.classList.add('animate__animated', animation);
    element.addEventListener('animationend', () => {
        element.classList.remove("animate__animated", animation);
    });
}