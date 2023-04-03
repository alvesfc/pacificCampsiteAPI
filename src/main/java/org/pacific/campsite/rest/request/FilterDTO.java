package org.pacific.campsite.rest.request;

import java.time.LocalDate;

public class FilterDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer page;
    private Integer size;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }


    public static final class Builder {
        private FilterDTO filterDTO;

        private Builder() {
            filterDTO = new FilterDTO();
        }

        public static Builder aFilterDTO() {
            return new Builder();
        }

        public Builder startDate(LocalDate startDate) {
            filterDTO.setStartDate(startDate);
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            filterDTO.setEndDate(endDate);
            return this;
        }

        public Builder page(Integer page) {
            filterDTO.setPage(page);
            return this;
        }

        public Builder size(Integer size) {
            filterDTO.setSize(size);
            return this;
        }

        public FilterDTO build() {
            return filterDTO;
        }
    }
}
