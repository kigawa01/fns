package net.kigawa.fns.frontend.component.post

import emotion.react.css
import js.core.asList
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.Channel
import net.kigawa.fns.frontend.component.user.UserManager
import net.kigawa.fns.frontend.util.ComponentBase
import net.kigawa.fns.share.ErrIDException
import net.kigawa.fns.share.json.FileJson
import net.kigawa.fns.share.json.post.PostPostBody
import net.kigawa.fns.share.util.concurrent.Coroutines
import net.kigawa.fns.share.util.io.DefaultIo
import react.ChildrenBuilder
import react.Fragment
import react.Props
import react.dom.html.ReactHTML
import react.router.Navigate
import react.useState
import web.cssom.Display
import web.cssom.px
import web.file.File
import web.file.FileReader
import web.html.InputType

object PostPost : ComponentBase<Props>() {
  override fun ChildrenBuilder.component(props: Props) {
    val (file, setFiles) = useState<File?>(null)
    val (title, setTitle) = useState("")
    val (err, setErr) = useState<String?>(null)
    val (thumbnail, setThumbnail) = useState<File?>(null)
    val user = UserManager.useUser()


    if (user.isReady && user.userInfo == null) Navigate {
      to = "/login?redirect=/post"
    }
    else ReactHTML.div {
      css {
        ReactHTML.label {
          display = Display.block
        }
      }
      if (err != null) ReactHTML.p {
        +err
      }
      else Fragment {}

      ReactHTML.label {
        +"タイトル"
        ReactHTML.input {
          type = InputType.text
          value = title
          onChange = { setTitle(it.currentTarget.value) }
        }
      }
      ReactHTML.label {
        +"投稿画像・動画・音声"
        ReactHTML.input {
          type = InputType.file
          accept = "audio/*,video/*,image/*"
          onChange = { setFiles(it.currentTarget.files?.asList()?.firstOrNull()) }
        }
      }
      ReactHTML.label {
        +"サムネ画像"
        ReactHTML.input {
          type = InputType.file
          accept = "image/*"
          onChange = { setThumbnail(it.currentTarget.files?.asList()?.firstOrNull()) }
        }
      }

      ReactHTML.button {
        css {
          marginTop = 10.px
          marginBottom = 10.px
          marginLeft = 20.px
          marginRight = 20.px
        }
        +"投稿"
        onClick = onClock@{
          if (title == "") {
            setErr("タイトルを入力してください")
            return@onClock
          }
          if (file == null) {
            setErr("投稿するファイルを指定してください")
            return@onClock
          }
          post(title, file, thumbnail)
        }
      }

    }
  }

  private fun post(title: String, file: File, thumbnail: File?) {
    Coroutines.launchIo {
      val reader = FileReader()
      val deferredFile = base64File(reader, file)
      val deferredThumbnail = thumbnail?.let { base64File(reader, it) }

      val postPostBody = PostPostBody(title, deferredFile.await(), deferredThumbnail?.await())
      val result = PostManager.postPost(postPostBody)
      val exception = result.exceptionOrNull()
      if (exception != null) {
        if (exception !is ErrIDException) {
          exception.printStackTrace()
          return@launchIo
        }
        DefaultIo.error.writeLine(exception.message)
        return@launchIo
      }
    }
  }

  private fun base64File(reader: FileReader, file: File): Deferred<FileJson> {
    return Coroutines.asyncIo {
      val result = Channel<String>()

      reader.onloadend = {
        Coroutines.launchIo {
          val target = it.target
          if (target == null) {
            console.error("fileReader is null")
            return@launchIo
          }
          if (target.readyState == FileReader.DONE) {
            result.send(target.result as String)
            result.close()
          }
        }
      }

      reader.readAsDataURL(file)
      return@asyncIo FileJson(file.name, file.type, result.receive())
    }
  }
}