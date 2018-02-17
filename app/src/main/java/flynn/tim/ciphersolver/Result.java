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
