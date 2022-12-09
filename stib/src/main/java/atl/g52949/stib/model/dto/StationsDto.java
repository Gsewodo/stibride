package atl.g52949.stib.model.dto;

public class StationsDto extends Dto<Integer> {

    private String name;

    public StationsDto(Integer key, String name) {
        super(key);
        this.name = name;
    }

    public StationsDto(Integer key) {
        super(key);
    }

    public Integer getKey() {
        return super.key;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
