package lincks.maximilian.wadloader2.domain3.rules;

import jakarta.persistence.*;
import lincks.maximilian.wadloader2.domain3.repos.WadRepo;
import lincks.maximilian.wadloader2.domain3.tags.Tag;
import lincks.maximilian.wadloader2.domain3.wads.WadPack;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Table(name = "Exclusive_Tag_Rule")
@Entity
public class ExclusiveTagRule implements WadPackRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //swapping firstSet and secondSet does not change the result
    @ElementCollection
    private Set<String> firstSet;
    @ElementCollection
    private Set<String> secondSet;

    public ExclusiveTagRule(Set<Tag> firstSet, Set<Tag> secondSet) {
        this.firstSet = firstSet.stream().map(Tag::tagId).collect(Collectors.toSet());
        this.secondSet = secondSet.stream().map(Tag::tagId).collect(Collectors.toSet());
    }

    protected ExclusiveTagRule() {}

    @Override
    public String toString() {
        return "ExclusiveTagRule{%n firstSet = %s%n secondSet = %s}".formatted(firstSet,secondSet);
    }

    @Override
    public Predicate<WadPack> getPredicate(WadRepo wadRepo) {
        return (WadPack wadPack) -> {
            Optional<Predicate<String>> containsTagPredicate = firstSet.stream()
                .<Predicate<String>>map(tagId -> tagId::equals)
                .reduce(Predicate::or);

        Optional<Predicate<String>> forbiddenTagsPredicate = secondSet.stream()
                .<Predicate<String>>map(tagId -> tagId::equals)
                .reduce(Predicate::or);

        return containsTagPredicate.isEmpty() ||
                //returns true if either one of the Sets is empty which implies that the optional is empty
                forbiddenTagsPredicate.isEmpty() ||
                //returns true if the WadPack does not contain tags from the firstSet
                TagRuleDomainService.getWadTagIds(wadPack,wadRepo)
                        .stream()
                        .noneMatch(containsTagPredicate.get()) ||
                //return true if none of the forbidden tags are contained
                TagRuleDomainService.getWadTagIds(wadPack,wadRepo)
                        .stream()
                        .noneMatch(forbiddenTagsPredicate.get());

        //can only return false if:
        // neither one of the Optionals is empty
        // the WadPack contains Tags from firstSet
        // the WadPack contains Tags from secondSet
        };
    }
}
