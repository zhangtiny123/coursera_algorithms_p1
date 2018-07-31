import java.lang.Integer;
import java.util.Random;

public class Percolation {
    int edgeSize;
    Site[] sites;
    int openSitesNumber;

    private class Site {
        int id;
        int parentId;
        int row;
        int col;
        int connectedSitesSize;
        boolean isOpen;

        Site(int id, int row, int col) {
            this.id = id;
            this.parentId = id;
            this.row = row;
            this.col = col;
            this.isOpen = false;
            this.connectedSitesSize = 0;
        }
    }

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must greater than 0");
        }
        this.edgeSize = n;
        this.sites = new Site[n*n + 2];
        Site virtualSiteAbove = new Site(0, 0, 0);
        Site virtualSiteBelow = new Site(n*n+1, n+1, n+1);
        virtualSiteAbove.isOpen = true;
        virtualSiteBelow.isOpen = true;

        sites[0] = virtualSiteAbove;
        sites[n*n + 1] = virtualSiteBelow;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int index = (i-1)*n + j;
                Site site = new Site(index, i, j);
                sites[index] = site;
            }
        }
    }

    public void open(int row, int col) {
        if (row < 1 || row > this.edgeSize || col < 1 || col > this.edgeSize) {
            throw new IllegalArgumentException("row and col must between 1 to n");
        }
        int index = (row - 1) * this.edgeSize + col;
        sites[index].isOpen = true;
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || row > this.edgeSize || col < 1 || col > this.edgeSize) {
            throw new IllegalArgumentException("row and col must between 1 to n");
        }
        return sites[(row-1) * this.edgeSize + col].isOpen;
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || row > this.edgeSize || col < 1 || col > this.edgeSize) {
            throw new IllegalArgumentException("row and col must between 1 to n");
        }

        int siteIndex = (row - 1) * this.edgeSize + col;
        return connected(sites[siteIndex], sites[0]);
    }

    public int numberOfOpenSites() {
        return this.openSitesNumber;
    }

    public Site root(Site site) {
        while(site.parentId != site.id) {
            site.parentId = sites[sites[site.parentId].parentId].id;
            site = sites[site.parentId];
        }
        return site;
    }

    private boolean connected(Site site1, Site site2) {
        Site site1Root = root(site1);
        Site site2Root = root(site2);
        return site1Root.id == site2Root.id;
    }

    public boolean percolates() {
        return connected(sites[0], sites[this.edgeSize*this.edgeSize + 1]);
    }

    public void union(Site site1, Site site2) {
        Site site1Root = root(site1);
        Site site2Root = root(site2);

        if(site1Root.id == site2Root.id) {
            return;
        }

        if (site1Root.connectedSitesSize < site2Root.connectedSitesSize) {
            site1Root.parentId = site2Root.id;
            site2Root.connectedSitesSize += site1Root.connectedSitesSize;
        } else {
            site2Root.parentId = site1Root.id;
            site1Root.connectedSitesSize += site2Root.connectedSitesSize;
        }
    }

    public static void main(String[] args) {
        System.out.println("what the fuck ???");
        int eSize;
        if (args.length != 0) {
            eSize = Integer.parseInt(args[0]);
        } else {
            eSize = 100;
        }

        Percolation pTest = new Percolation(eSize);
        int sitesLen = eSize * eSize + 2;
        Random rand = new Random();

        while(!pTest.connected(pTest.sites[0], pTest.sites[sitesLen - 1])) {
            System.out.println("what the fuck loop???");
            int randIndex = rand.nextInt(sitesLen - 2) + 1;
            Site site = pTest.sites[randIndex];
            if (site.isOpen) {
                continue;
            } else {
                pTest.open(site.row, site.col);
                pTest.openSitesNumber += 1;

                int upTempIndex = site.id - eSize;
                int upIndex = upTempIndex > 0 ? upTempIndex : 0;
                int downTempIndex = site.id + eSize;
                int downIndex = downTempIndex < sitesLen - 1 ? downTempIndex : sitesLen - 1;
                int leftTempIndex = site.id - 1;
                int leftIndex = leftTempIndex % eSize == 0 ? -1 : leftTempIndex;
                int rightTempIndex = site.id + 1;
                int rightIndex = rightTempIndex % eSize == 1 ? -1 : rightTempIndex;

                Site upSite = pTest.sites[upIndex];
                if (upSite.isOpen) {
                    pTest.union(site, upSite);
                }
                Site downSite = pTest.sites[downIndex];
                if (downSite.isOpen) {
                    pTest.union(site, downSite);
                }
                if (leftIndex != -1) {
                    Site leftSite = pTest.sites[leftIndex];
                    if (leftSite.isOpen) {
                        pTest.union(site, leftSite);
                    }
                }
                if (rightIndex != -1) {
                    Site rightSite = pTest.sites[rightIndex];
                    if (rightSite.isOpen) {
                        pTest.union(site, rightSite);
                    }
                }
            }
        }

        System.out.println("percolation finished");
        System.out.println(pTest.numberOfOpenSites());
        System.out.println(sitesLen - 2);
        System.out.println((double)pTest.numberOfOpenSites() / (double)(sitesLen - 2));


    }
}