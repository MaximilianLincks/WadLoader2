package lincks.maximilian.wadloader2.plugins.jpa.repository;

import lincks.maximilian.wadloader2.domain.tags.DefaultTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultTagSpringRepo extends JpaRepository<DefaultTag, String> {
}