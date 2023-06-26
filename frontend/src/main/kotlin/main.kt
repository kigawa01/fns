import component.Root
import react.create
import react.dom.client.createRoot
import web.dom.document

fun main() {
    document
        .getElementById("react")
        ?.let { createRoot(it) }
        ?.render(Root.create())
        ?: document.write("ページのロードに失敗しました")

}
