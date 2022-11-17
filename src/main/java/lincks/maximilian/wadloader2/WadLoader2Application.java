package lincks.maximilian.wadloader2;

import lincks.maximilian.wadloader2.domain.WadFileFinder;
import lincks.maximilian.wadloader2.domain.tags.Tag;
import lincks.maximilian.wadloader2.domain.wads.IWad;
import lincks.maximilian.wadloader2.domain.wads.Wad;
import lincks.maximilian.wadloader2.plugins.jpa.repository.bridge.IWadBridge;
import lincks.maximilian.wadloader2.plugins.jpa.repository.bridge.WadBridge;
import lincks.maximilian.wadloader2.plugins.ui.UIBase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.awt.*;
import java.nio.file.Path;

@SpringBootApplication
@RequiredArgsConstructor
public class WadLoader2Application{

    //TODO: build fitting ui

    public static void main(String[] args) {
        new SpringApplicationBuilder(WadLoader2Application.class)
                .web(WebApplicationType.NONE)
                .headless(false)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    final WadFileFinder wadFinder;
    final WadBridge wadService;
    final IWadBridge iWadService;

    @EventListener(ApplicationReadyEvent.class)
    public void appStartup(){

        EventQueue.invokeLater(() -> {
            UIBase ui = new UIBase();
            ui.initUI();
            ui.setVisible(true);
        });

		wadFinder.findWads(Path.of("D:\\GZDoom\\wads"))
				.stream()
				.map(wadService::save)
				.map(Wad::getWadTag)
				.map(Tag::tagName)
				.forEach(System.out::println);

		wadFinder.findIWads(Path.of("D:\\GZDoom\\wads\\iwads"))
				.stream()
				.map(iWadService::save)
				.map(IWad::getWadTag)
				.map(Tag::tagName)
				.forEach(System.out::println);
    }
}
