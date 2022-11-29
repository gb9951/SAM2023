package mdd.team4.sam2023.models.files;

import mdd.team4.sam2023.models.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class File extends BaseEntity {
    String name;
    String type;

    @Lob
    byte[] data;

    public File(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public File() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
