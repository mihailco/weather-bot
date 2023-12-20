package com.orgyflame.springtelegrambotapi.api.object.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orgyflame.springtelegrambotapi.api.object.InputFile;
import com.orgyflame.springtelegrambotapi.api.object.MessageEntity;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 * <p>
 * Represents an animation file (GIF or H.264/MPEG-4 AVC video without sound) to be sent.
 */
@SuppressWarnings("unused")
@JsonDeserialize
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class InputMediaAnimation extends InputMedia {
    public static final String WIDTH_FIELD = "width";
    public static final String HEIGHT_FIELD = "height";
    public static final String DURATION_FIELD = "duration";
    public static final String THUMB_FIELD = "thumb";
    private static final String TYPE = "animation";
    @JsonProperty(WIDTH_FIELD)
    private Integer width; ///< Optional. Animation width
    @JsonProperty(HEIGHT_FIELD)
    private Integer height; ///< Optional. Animation height
    @JsonProperty(DURATION_FIELD)
    private Integer duration; ///< Optional. Animation duration
    /**
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail’s width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumb;

    public InputMediaAnimation() {
        super();
    }

    public InputMediaAnimation(@NonNull String media) {
        super(media);
    }

    @Builder
    public InputMediaAnimation(@NonNull String media, String caption, String parseMode, List<MessageEntity> entities, boolean isNewMedia, String mediaName, File newMediaFile, InputStream newMediaStream, Integer width, Integer height, Integer duration, InputFile thumb) {
        super(media, caption, parseMode, entities, isNewMedia, mediaName, newMediaFile, newMediaStream);
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.thumb = thumb;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        super.validate();
    }
}
