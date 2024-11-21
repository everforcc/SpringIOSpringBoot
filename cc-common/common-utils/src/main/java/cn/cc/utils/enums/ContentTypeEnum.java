package cn.cc.utils.enums;

/**
 * HTTP Content-type
 * https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
 *
 */
public enum ContentTypeEnum {
    // 二进制流
    stream(".*（ 二进制流）", "application/octet-stream"),
    aac(".aac", "audio/aac"),
    abw(".abw", "application/x-abiword"),
    arc(".arc", "application/x-freearc"),
    avi(".avi", "video/x-msvideo"),
    azw(".azw", "application/vnd.amazon.ebook"),
    bin(".bin", "application/octet-stream"),
    bmp(".bmp", "image/bmp"),
    bz(".bz", "application/x-bzip"),
    bz2(".bz2", "application/x-bzip2"),
    csh(".csh", "application/x-csh"),
    css(".css", "text/css"),
    csv(".csv", "text/csv"),
    conf(".conf", "text/plain"),
    doc(".doc", "application/msword"),
    docx(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    eot(".eot", "application/vnd.ms-fontobject"),
    epub(".epub", "application/epub+zip"),
    gif(".gif", "image/gif"),
    htm(".htm", "text/html"),
    html(".html", "text/html"),
    html_UTF_8(".html", "text/html;charset=utf-8"),
    //ico(".ico", "image/x-icon"),
    ico(".ico", "image/vnd.microsoft.icon"),
    ics(".ics", "text/calendar"),
    jar(".jar", "application/java-archive"),
    jpeg(".jpeg", "image/jpeg"),
    jpg(".jpg", "image/jpeg"),
    //js(".js", "application/javascript"),
    js(".js", "text/javascript"),
    json(".json", "application/json"),
    jsonld(".jsonld", "application/ld+json"),
    log(".log", "text/plain"),
    mid(".mid", "audio/midi"),
    midi(".midi", "audio/x-midi"),
    mjs(".mjs", "text/javascript"),
    mp3(".mp3", "audio/mpeg"),
    mpeg(".mpeg", "video/mpeg"),
    mpkg(".mpkg", "application/vnd.apple.installer+xml"),
    odp(".odp", "application/vnd.oasis.opendocument.presentation"),
    ods(".ods", "application/vnd.oasis.opendocument.spreadsheet"),
    odt(".odt", "application/vnd.oasis.opendocument.text"),
    oga(".oga", "audio/ogg"),
    ogv(".ogv", "video/ogg"),
    ogx(".ogx", "application/ogg"),
    otf(".otf", "font/otf"),
    png(".png", "image/png"),
    pdf(".pdf", "application/pdf"),
    pem(".pem", "text/plain"),
    pkcs8(".pkcs8", "text/plain"),
    ppt(".ppt", "application/vnd.ms-powerpoint"),
    pptx(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    rar(".rar", "application/x-rar-compressed"),
    rtf(".rtf", "application/rtf"),
    sh(".sh", "application/x-sh"),
    sql(".sql", "text/plain"),
    svg(".svg", "image/svg+xml"),
    swf(".swf", "application/x-shockwave-flash"),
    tar(".tar", "application/x-tar"),
    tif(".tif", "image/tiff"),
    tiff(".tiff", "image/tiff"),
    ttf(".ttf", "font/ttf"),
    txt(".txt", "text/plain"),
    txt_UTF_8(".txt", "text/plain;charset=utf-8"),
    vsd(".vsd", "application/vnd.visio"),
    wav(".wav", "audio/wav"),
    weba(".weba", "audio/webm"),
    webm(".webm", "video/webm"),
    webp(".webp", "image/webp"),
    woff(".woff", "font/woff"),
    woff2(".woff2", "font/woff2"),
    xhtml(".xhtml", "application/xhtml+xml"),
    xls(".xls", "application/vnd.ms-excel"),
    xlsx(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    //xml(".xml", "application/xml"),
    xml(".xml", "text/xml"),
    xul(".xul", "application/vnd.mozilla.xul+xml"),
    zip(".zip", "application/zip"),
    ;

    public final String comment;
    public final String type;

    ContentTypeEnum(String comment, String type) {
        this.comment = comment;
        this.type = type;
    }

    public String utf8() {
        return type.concat(";charset=utf-8");
    }

}
