package com.unisatc.eco_report.seeds;

import com.unisatc.eco_report.category.Category;
import com.unisatc.eco_report.category.CategoryRepository;
import com.unisatc.eco_report.collectionpoint.CollectionPoint;
import com.unisatc.eco_report.collectionpoint.CollectionPointRepository;
import com.unisatc.eco_report.report.Report;
import com.unisatc.eco_report.report.ReportRepository;
import com.unisatc.eco_report.reportImage.ReportImage;
import com.unisatc.eco_report.user.User;
import com.unisatc.eco_report.user.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(name = "app.seed", havingValue = "true")
public class SeedData implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SeedData.class);

    private final ReportRepository reportRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CollectionPointRepository collectionPointRepository;

    public SeedData(
            ReportRepository reportRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository,
            CollectionPointRepository collectionPointRepository) {
        this.reportRepository = reportRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.collectionPointRepository = collectionPointRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Executando seeds...");

        seedCategories();
        seedUsers();
        seedCollectionPoints();
        seedReports();

        log.info("Seeds executados!");
    }


    private void seedCategories() {
        if (categoryRepository.count() > 0) {
            log.info("Categorias já existem, pulando.");
            return;
        }

        createCategory("Descarte irregular de lixo");
        createCategory("Poluição");
        createCategory("Desmatamento");
        createCategory("Proteção animal");
    }

    private void createCategory(String name) {
        Category category = Category.create();
        category.setName(name);

        categoryRepository.save(category);
        log.info("Categoria criada: {}", name);
    }

    private static final String CITIZEN_HASH = "$2a$10$LMVeED85LXtKf/.47F7ZZ.p70AHLCruM9Jt6UrYlTPIuXga7Ixw1a"; // senha123

    private void seedUsers() {
        if (userRepository.count() > 0) {
            log.info("Usuários já existem, pulando.");
            return;
        }

        createUser("Administrador", "admin@ecoreport.com", CITIZEN_HASH);
        createUser("Maria Silva", "maria@email.com", CITIZEN_HASH);
        createUser("João Pereira", "joao@email.com", CITIZEN_HASH);
    }

    private void createUser(String name, String email, String passwordHash) {
        User user = User.create();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);

        userRepository.save(user);
        log.info("Usuário criado: {}", email);
    }


    private void seedCollectionPoints() {
        if (collectionPointRepository.count() > 0) {
            log.info("Pontos de coleta já existem, pulando.");
            return;
        }

        createCollectionPoint(
                "Ecoponto Centro",
                "Rua Marechal Deodoro, 300 - Centro, Criciúma - SC",
                "Papel, plástico, vidro, metal",
                "Seg a Sex: 08h às 17h");

        createCollectionPoint(
                "Ecoponto Pinheirinho",
                "Av. Santos Dumont, 1200 - Pinheirinho, Criciúma - SC",
                "Eletrônicos, pilhas, baterias, lâmpadas",
                "Seg a Sáb: 09h às 16h");

        createCollectionPoint(
                "Ponto de Coleta de Óleo de Cozinha",
                "Rua Henrique Lage, 450 - Centro, Criciúma - SC",
                "Óleo de cozinha usado",
                "Seg a Sex: 08h às 18h");

        createCollectionPoint(
                "Ecoponto Universitário",
                "Rodovia Governador Jorge Lacerda, s/n - Criciúma - SC",
                "Papel, plástico, metal, tampinhas",
                null);
    }

    private void createCollectionPoint(
            String name,
            String address,
            String acceptedMaterials,
            String openingHours) {

        CollectionPoint point = CollectionPoint.create();
        point.setName(name);
        point.setAddress(address);
        point.setAcceptedMaterials(acceptedMaterials);
        point.setOpeningHours(openingHours);

        collectionPointRepository.save(point);
        log.info("Ponto de coleta criado: {}", name);
    }


    private void seedReports() {
        if (reportRepository.count() > 0) {
            log.info("Reports já existem, pulando.");
            return;
        }

        // Usa só findAll() (já existe no JpaRepository) e indexa em memória
        Map<String, Category> categories = new HashMap<>();
        for (Category c : categoryRepository.findAll()) {
            categories.put(c.getName(), c);
        }

        Map<String, User> users = new HashMap<>();
        for (User u : userRepository.findAll()) {
            users.put(u.getEmail(), u);
        }

        Category lixo = requireCategory(categories, "Descarte irregular de lixo");
        Category poluicao = requireCategory(categories, "Poluição");
        Category desmatamento = requireCategory(categories, "Desmatamento");
        Category animal = requireCategory(categories, "Proteção animal");

        User maria = requireUser(users, "maria@email.com");
        User joao = requireUser(users, "joao@email.com");

        createReport(
                "Lixo acumulado na calçada",
                "Resíduos descartados em local inadequado, atraindo insetos e roedores.",
                "Rua das Flores, 100 - Criciúma - SC",
                lixo, maria,
                List.of(
                        "https://picsum.photos/seed/lixo1/800/600",
                        "https://picsum.photos/seed/lixo2/800/600"));

        createReport(
                "Acúmulo de entulho",
                "Entulho de obra acumulado próximo à calçada, dificultando a passagem.",
                "Avenida Central, 250 - Criciúma - SC",
                lixo, joao,
                List.of("https://picsum.photos/seed/entulho1/800/600"));

        createReport(
                "Esgoto a céu aberto",
                "Esgoto escorrendo para o córrego, com forte odor e água turva.",
                "Rua dos Ipês, 45 - Criciúma - SC",
                poluicao, maria,
                List.of("https://picsum.photos/seed/esgoto1/800/600"));

        createReport(
                "Corte de árvores em área de preservação",
                "Árvores nativas derrubadas em terreno próximo ao rio.",
                "Estrada Geral do Rio Cocal, km 3 - Criciúma - SC",
                desmatamento, joao,
                List.of(
                        "https://picsum.photos/seed/mata1/800/600",
                        "https://picsum.photos/seed/mata2/800/600"));

        createReport(
                "Cachorro abandonado na rua",
                "Animal aparentemente machucado, sem coleira, vagando há dias pela região.",
                "Rua Pedro Álvares Cabral, 78 - Criciúma - SC",
                animal, maria,
                List.of());
    }

    private void createReport(
            String title,
            String description,
            String location,
            Category category,
            User user,
            List<String> imageUrls) {

        Report report = Report.create();
        report.setTitle(title);
        report.setDescription(description);
        report.setLocation(location);
        report.setCategory(category);
        report.setUser(user);
        report.setStatus("PENDING");
        report.setDeleted(false);

        // Report tem cascade = ALL em images: salvar o report salva as imagens.
        for (String url : imageUrls) {
            ReportImage image = ReportImage.create();
            image.setReport(report);
            image.setImageUrl(url);
            report.addImage(image);
        }

        reportRepository.save(report);
        log.info("Report criado: {} ({} imagens)", title, imageUrls.size());
    }


    private Category requireCategory(Map<String, Category> categories, String name) {
        Category category = categories.get(name);
        if (category == null) {
            throw new IllegalStateException("Categoria não encontrada: " + name);
        }
        return category;
    }

    private User requireUser(Map<String, User> users, String email) {
        User user = users.get(email);
        if (user == null) {
            throw new IllegalStateException("Usuário não encontrado: " + email);
        }
        return user;
    }
}