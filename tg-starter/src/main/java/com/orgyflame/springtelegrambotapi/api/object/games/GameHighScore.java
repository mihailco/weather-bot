/*
 * This file is part of TelegramBots.
 *
 * TelegramBots is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TelegramBots is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TelegramBots.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orgyflame.springtelegrambotapi.api.object.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import com.orgyflame.springtelegrambotapi.api.object.User;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents one row of a game high scores table
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameHighScore implements BotApiObject {
    private static final String POSITION_FIELD = "position";
    private static final String USER_FIELD = "user";
    private static final String SCORE_FIELD = "score";

    @JsonProperty(POSITION_FIELD)
    @NonNull
    private Integer position; ///< Position in the game high score table
    @JsonProperty(USER_FIELD)
    @NonNull
    private User user; ///< User
    @JsonProperty(SCORE_FIELD)
    @NonNull
    private Integer score; ///< Score
}
