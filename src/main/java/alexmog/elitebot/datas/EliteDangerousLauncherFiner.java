package alexmog.elitebot.datas;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class EliteDangerousLauncherFiner extends SimpleFileVisitor<Path> {
    private static final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:EDLaunch.exe");
    private List<File> mPathsFound = new ArrayList<>();

    // Invoke the pattern matching
    // method on each file.
    @Override
    public FileVisitResult visitFile(Path file,
            BasicFileAttributes attrs) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            System.out.println("Possible find: " + file);
            File productsDir = new File(file.getParent() + File.separator + "Products");
            if (productsDir.exists() && productsDir.isDirectory()) {
                System.out.println("Products dir found: " + productsDir);
                productsDir.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        File f = new File(dir, name);
                        if (f.isDirectory()) {
                            System.out.println(f);
                            mPathsFound.add(f);
                            return true;
                        }
                        return false;
                    }
                });
            }
            return mPathsFound.size() > 0 ? FileVisitResult.TERMINATE : FileVisitResult.CONTINUE;
        }
        return FileVisitResult.CONTINUE;
    }
    
    public List<File> getPathsFound() {
        return mPathsFound;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,
            IOException exc) {
        System.err.println(exc);
        return FileVisitResult.CONTINUE;
    }
}
