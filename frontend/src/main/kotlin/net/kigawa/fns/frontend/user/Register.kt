package net.kigawa.fns.frontend.user

import emotion.react.css
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.kigawa.fns.frontend.RouteList
import net.kigawa.fns.frontend.page.PageBase
import net.kigawa.fns.frontend.service.TokenManager
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.frontend.util.KutilUrl
import net.kigawa.fns.frontend.util.hook.ThemeProvider
import react.*
import react.dom.html.ReactHTML
import react.router.Navigate
import web.cssom.NamedColor
import web.cssom.pct
import web.cssom.px
import web.cssom.rem
import web.html.InputType

object Register : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    val (name, setName) = useState("")
    val (pass, setPass) = useState("")
    val (email, setEmail) = useState("")
    val (err, setErr) = useState<String>()
    val (redirect, setRedirect) = useState<String>()

    useEffectOnce {
      if (TokenManager.isLogin()) setRedirect(RouteList.TOP.strPath)
    }

    redirect?.let {
      Navigate {
        replace = true
        to = KutilUrl.createURL(it).pathname
      }
    } ?: PageBase.fc {
      css { paddingBottom = 0.1.px }
      ReactHTML.div {
        style()
        ReactHTML.h2 {
          +"ユーザー登録"
        }
        err?.let {
          ReactHTML.p {
            css {
              color = NamedColor.red
            }
            +err
          }
        }
        ReactHTML.h3 {
          +"ユーザー名"
        }
        ReactHTML.input {
          type = InputType.text
          onChange = { setName(it.currentTarget.value) }
          value = name
        }
        ReactHTML.h3 {
          +"パスワード"
        }
        ReactHTML.input {
          type = InputType.password
          onChange = { setPass(it.currentTarget.value) }
          value = pass
        }
        ReactHTML.h3 {
          +"メールアドレス"
        }
        ReactHTML.input {
          type = InputType.email
          onChange = { setEmail(it.currentTarget.value) }
          value = email
        }
        ReactHTML.div {
          ReactHTML.button {
            +"登録"
            onClick = onClick@{
              setErr(null)
              val button = it.currentTarget
              button.disabled = true
              console.info("on click")
              CoroutineScope(Dispatchers.Default).launch {
                val result = UserManager.register(name, pass, email)

                val exception = result.exceptionOrNull()
                if (exception == null) {
                  val url = KutilUrl.createURL().searchParams["redirect"] ?: RouteList.TOP.strPath
                  setRedirect(url)
                } else {
                  setErr(exception.message ?: exception.toString())
                  button.disabled = false
                }
              }
            }
          }
        }

      }
    }
  }

  private fun PropsWithClassName.style() {
    val theme = ThemeProvider.use()
    return css {
      marginLeft = web.cssom.Auto.auto
      marginRight = web.cssom.Auto.auto
      marginTop = 100.px
      marginBottom = 100.px
      width = 350.px
      backgroundColor = web.cssom.Color(theme.base2)
      padding = 20.px

      ReactHTML.h2 {
        fontSize = 1.8.rem
      }
      ReactHTML.h3 {
        marginTop = 20.px
        fontSize = 1.2.rem
      }
      ReactHTML.input {
        marginTop = 5.px
        width = 100.pct
        padding = 3.px
        borderColor = web.cssom.Color(theme.main)
      }
      ReactHTML.div {
        marginTop = 20.px
        display = web.cssom.Display.flex
        justifyContent = web.cssom.JustifyContent.flexEnd

        ReactHTML.button {

          paddingLeft = 10.px
          paddingRight = 10.px
          paddingTop = 5.px
          paddingBottom = 5.px
          marginLeft = web.cssom.Auto.auto
          backgroundColor = web.cssom.Color(theme.accent)
          fontSize = 1.1.rem

          "&:hover:enabled" {
            opacity = web.cssom.number(0.5)
          }
          "&:disabled" {
            opacity = web.cssom.number(0.5)
          }
        }
      }

    }
  }

}