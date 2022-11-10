package lincks.maximilian.wadloader2.model.tags;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


/**
 * Tag which groups Elements of a Wad-Pack
*/
@AllArgsConstructor
@Entity
@Table(name = "Wad_Pack_Tags")
public class WadPackTag implements Tag{

    protected WadPackTag() {

    }

    private static final TagType tagType = TagType.WAD_PACK_TAG;
    @Column
    @Id
    private String name;

    @Override
    public String tagName() {
        return name;
    }

    @Override
    public TagType tagType() {
        return tagType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WadPackTag that = (WadPackTag) o;
        return name != null && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
