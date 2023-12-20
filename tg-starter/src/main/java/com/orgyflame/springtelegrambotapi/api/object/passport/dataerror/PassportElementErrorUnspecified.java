package com.orgyflame.springtelegrambotapi.api.object.passport.dataerror;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 4.1
 * <p>
 * Represents an issue in an unspecified place.
 * The error is considered resolved when new data is added.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassportElementErrorUnspecified implements PassportElementError {
    private static final String SOURCE_FIELD = "source";
    private static final String TYPE_FIELD = "type";
    private static final String ELEMENTHASH_FIELD = "element_hash";
    private static final String MESSAGE_FIELD = "message";

    @JsonProperty(SOURCE_FIELD)
    private final String source = "unspecified"; ///< Error source, must be unspecified
    /**
     * Type of element of the user's Telegram Passport which has the issue
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    private String type;
    @JsonProperty(ELEMENTHASH_FIELD)
    @NonNull
    private String elementHash; ///< Base64-encoded element hash
    @JsonProperty(MESSAGE_FIELD)
    @NonNull
    private String message; ///< Error message

    @Override
    public void validate() throws TelegramApiValidationException {
        if (elementHash == null || elementHash.isEmpty()) {
            throw new TelegramApiValidationException("Element hash parameter can't be empty", this);
        }
        if (message == null || message.isEmpty()) {
            throw new TelegramApiValidationException("Message parameter can't be empty", this);
        }
        if (type == null || type.isEmpty()) {
            throw new TelegramApiValidationException("Type parameter can't be empty", this);
        }
    }
}
