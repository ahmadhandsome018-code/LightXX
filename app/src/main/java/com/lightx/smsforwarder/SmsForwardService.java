private String buildEmailJson(String subject, String content) {
    StringBuilder sb = new StringBuilder();

    sb.append("{");
    sb.append("\"sender\": {\"name\": \"LightX SMS Forwarder\", \"email\": \"")
            .append(escapeJson("alahlymasr9@gmail.com"))
            .append("\"},");

    sb.append("\"to\": [{\"email\": \"")
            .append(escapeJson("ainsports12@gmail.com"))
            .append("\"}],");

    sb.append("\"subject\": \"")
            .append(escapeJson(subject))
            .append("\",");

    sb.append("\"htmlContent\": \"")
            .append(escapeJson(content))
            .append("\"");

    sb.append("}");

    return sb.toString();
}
