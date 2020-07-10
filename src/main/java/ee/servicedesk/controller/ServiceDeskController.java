package ee.servicedesk.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ee.servicedesk.model.Ticket;
import ee.servicedesk.model.TicketPriority;
import ee.servicedesk.model.TicketStatus;
import ee.servicedesk.service.TicketService;

/**
 * Controller for handling tickets requests mapping for Spring MVC
 *
 * @author Andro Põlluste
 */
@Controller
public class ServiceDeskController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/")
    public String indexView(Model model)  {
        List<Ticket> activeTickets = ticketService.findAllActive();
        model.addAttribute("tickets", activeTickets);
        return "index";
    }

    @GetMapping("/create")
    public String createTicketView(Model model)  {
        addStatusesAndPriorities(model);
        model.addAttribute("ticket", new Ticket());
        return "ticketForm";
    }

    @GetMapping("/edit/{id}")
    public String editTicketView(@PathVariable Long id, Model model)  {
        Optional<Ticket> ticket = ticketService.find(id);
        if (ticket.isEmpty()) {
            model.addAttribute("error", String.format("Ticket with id %d not found", id));
            model.addAttribute("ticket", new Ticket());
        } else {
            model.addAttribute("ticket", ticket.get());
        }
        addStatusesAndPriorities(model);
        return "ticketForm";
    }

    @PostMapping("/save")
    public String save(@Valid Ticket ticket, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (ticket.isNew()) {
            handleCreate(ticket, bindingResult, redirectAttributes);
        } else {
            handleUpdate(ticket, bindingResult, redirectAttributes);
        }
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean exists = ticketService.find(id).isPresent();
        if (exists) {
            ticketService.delete(id);
            redirectAttributes.addFlashAttribute("success", String.format("Ticket %d deleted successfully", id));
        } else {
            redirectAttributes.addFlashAttribute("error", String.format("Ticket %d not found", id));
        }
        return "redirect:/";
    }

    //////////////////////
    // PRIVATE METHODS
    //////////////////////

    private void handleCreate(Ticket ticket, Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            addStatusesAndPriorities(redirectAttributes);
            redirectAttributes.addFlashAttribute("ticket", ticket);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ticket", errors);
        } else {
            Ticket savedTicket = ticketService.create(ticket);
            redirectAttributes.addFlashAttribute("success", String.format("Ticket %d created successfully!", savedTicket.getId()));
        }
    }

    private void handleUpdate(Ticket ticket, Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            addStatusesAndPriorities(redirectAttributes);
            redirectAttributes.addFlashAttribute("ticket", ticket);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ticket", errors);

        } else {
            Ticket savedTicket = ticketService.update(ticket);
            redirectAttributes.addFlashAttribute("success", String.format("Ticket %d updated successfully!", savedTicket.getId()));
        }
    }

    private static void addStatusesAndPriorities(Model model) {
        if (model instanceof RedirectAttributes) {
            ((RedirectAttributes)model).addFlashAttribute("priorities", TicketPriority.values());
            ((RedirectAttributes)model).addFlashAttribute("statuses", TicketStatus.values());
        } else {
            model.addAttribute("priorities", TicketPriority.values());
            model.addAttribute("statuses", TicketStatus.values());
        }
    }

}
