package com.orgyflame.springtelegrambotapi.api.object.inlinequery.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orgyflame.springtelegrambotapi.api.object.MessageEntity;
import com.orgyflame.springtelegrambotapi.api.object.inlinequery.inputmessagecontent.InputMessageContent;
import com.orgyflame.springtelegrambotapi.api.object.replykeyboard.InlineKeyboardMarkup;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Represents a link to an animated GIF file. By default, this animated GIF file will be sent
 * by the user with optional caption. Alternatively, you can use input_message_content to send a
 * message with the specified content instead of the animation.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InlineQueryResultGif implements InlineQueryResult {
    private static final List<String> VALIDTHUMBTYPES = Collections.unmodifiableList(Arrays.asList("image/jpeg", "image/gif", "video/mp4"));

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String GIFURL_FIELD = "gif_url";
    private static final String GIFWIDTH_FIELD = "gif_width";
    private static final String GIFHEIGHT_FIELD = "gif_height";
    private static final String THUMBURL_FIELD = "thumb_url";
    private static final String THUMBMIMETYPE_FIELD = "thumb_mime_type";
    private static final String TITLE_FIELD = "title";
    private static final String CAPTION_FIELD = "caption";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String GIF_DURATION_FIELD = "gif_duration";
    private static final String PARSEMODE_FIELD = "parse_mode";
    private static final String CAPTION_ENTITIES_FIELD = "caption_entities";

    @JsonProperty(TYPE_FIELD)
    private final String type = "gif"; ///< Type of the result, must be "gif"
    @JsonProperty(ID_FIELD)
    @NonNull
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(GIFURL_FIELD)
    @NonNull
    private String gifUrl; ///< A valid URL for the GIF file. File size must not exceed 1MB
    @JsonProperty(GIFWIDTH_FIELD)
    private Integer gifWidth; ///< Optional. Width of the GIF
    @JsonProperty(GIFHEIGHT_FIELD)
    private Integer gifHeight; ///< Optional. Height of the GIF
    @JsonProperty(THUMBURL_FIELD)
    private String thumbUrl; ///< Optional. URL of the static (JPEG or GIF) or animated (MPEG4) thumbnail for the result
    @JsonProperty(THUMBMIMETYPE_FIELD)
    private String thumbUrlType; ///< Optional. MIME type of the thumbnail, must be one of “image/jpeg”, “image/gif”, or “video/mp4”
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Optional. Title for the result
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption of the GIF file to be sent
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent instead of the GIF animation
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message
    @JsonProperty(GIF_DURATION_FIELD)
    private Integer gifDuration; ///< Optional. Duration of the GIF
    @JsonProperty(PARSEMODE_FIELD)
    private String parseMode; ///< Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
    @JsonProperty(CAPTION_ENTITIES_FIELD)
    @Singular
    private List<MessageEntity> captionEntities; ///< Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (gifUrl == null || gifUrl.isEmpty()) {
            throw new TelegramApiValidationException("GifUrl parameter can't be empty", this);
        }
        if (thumbUrlType != null && !VALIDTHUMBTYPES.contains(thumbUrlType)) {
            throw new TelegramApiValidationException("ThumbUrlType parameter must be one of “image/jpeg”, “image/gif”, or “video/mp4”", this);
        }
        if (parseMode != null && (captionEntities != null && !captionEntities.isEmpty())) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }
        if (inputMessageContent != null) {
            inputMessageContent.validate();
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }
}
