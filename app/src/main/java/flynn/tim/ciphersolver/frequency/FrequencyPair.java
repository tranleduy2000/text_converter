/*
 * Copyright (c) 2018 by Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package flynn.tim.ciphersolver.frequency;

/**
 * Created by Tim on 8/19/2015.
 */
class FrequencyPair {
    private String character;
    private int value;

    FrequencyPair(String character, int value) {
        this.character = character;
        this.value = value;
    }

    public String getCharacter() {
        return this.character;
    }

    public int getValue() {
        return this.value;
    }
}
