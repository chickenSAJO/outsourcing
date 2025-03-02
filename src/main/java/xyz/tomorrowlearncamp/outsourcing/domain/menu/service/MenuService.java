package xyz.tomorrowlearncamp.outsourcing.domain.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.tomorrowlearncamp.outsourcing.domain.menu.repository.MenuRepository;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
}
