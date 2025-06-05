package edu.fdu.ivos.base.file;

import edu.fdu.ivos.base.response.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/file")
public class UploadController {
    /*
     * Method to handle image upload functionality
     */
    @PostMapping("upload")
    // MultipartFile is used to receive the uploaded image file
    // Note: the parameter name `file` must match the name attribute of the upload component on the frontend
    public JsonResult upload(MultipartFile file) throws IOException {
        // 1. Get the original file name
        String fileName = file.getOriginalFilename();
        // 2. Get the file extension
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 3. Generate a unique random string as the new file name
        fileName = UUID.randomUUID() + suffix;
        // 4. Specify the directory path to store uploaded files (this path must exist on your local machine)
        String dirPath = "d:/files";
        // 5. To improve retrieval performance, use a date-based subdirectory (e.g., 2024/07/15)
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
        // 6. Format the current date as a folder path
        String datePath = sdf.format(new Date());
        // 7. Combine base path and date path into a full directory path
        File dirFile = new File(dirPath + datePath);
        // 8. If the directory doesn't exist, create it (including all parent directories)
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        // 9. Build the complete file path
        String filePath = dirPath + datePath + fileName;
        // 10. Save the uploaded file to the specified directory
        file.transferTo(new File(filePath));
        // 11. Return the relative file path to the frontend for preview
        return JsonResult.ok(datePath + fileName);
    }

    @PostMapping("remove")
    public JsonResult remove(String imgUrl) {
        log.debug("Deleting image: imgUrl={}", imgUrl);
        // Build the full path and delete the corresponding file from disk
        new File("d:/files" + imgUrl).delete();
        return JsonResult.ok();
    }
}
