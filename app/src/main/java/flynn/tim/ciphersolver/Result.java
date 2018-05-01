/*
 * Copyright (C)  2017-2018 Tran Le Duy
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package flynn.tim.ciphersolver;

/**
 * Created by Tim on 3/20/2015.
 */
public class Result {

    private String result;
    private Boolean checked;
    private Boolean ex;

    public Result(String result, Boolean checked, Boolean ex) {
        this.result = result;
        this.checked = checked;
        this.ex = ex;
    }

    public void setResult(String r) {
        this.result = r;
    }

    public String getResult() {
        return this.result;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public void setEx(Boolean ex) {
        this.ex = ex;
    }

    public boolean getEx() {
        return this.ex;
    }
}
