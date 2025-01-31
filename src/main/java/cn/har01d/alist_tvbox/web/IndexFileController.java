package cn.har01d.alist_tvbox.web;

import cn.har01d.alist_tvbox.service.IndexFileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/index-files")
public class IndexFileController {
    private final IndexFileService service;

    public IndexFileController(IndexFileService service) {
        this.service = service;
    }

    @GetMapping
    public Page<String> getIndexContent(Pageable pageable, String siteId) throws IOException {
        return service.getIndexContent(pageable, siteId);
    }

    @PostMapping("/exclude")
    public void toggleExcluded(String siteId, int index) throws IOException {
        service.toggleExcluded(siteId, index);
    }

    @PostMapping("/upload")
    public void uploadIndexFile(String siteId, @RequestParam("file") MultipartFile file) throws IOException {
        service.uploadIndexFile(siteId, file);
    }

    @GetMapping("/download")
    public FileSystemResource downloadIndexFile(String siteId, HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "attachment; filename=\"index.zip\"");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return service.downloadIndexFile(siteId);
    }
}
