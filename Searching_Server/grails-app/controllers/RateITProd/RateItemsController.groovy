package RateITProd

import org.springframework.dao.DataIntegrityViolationException

class RateItemsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [rateItemsInstanceList: RateItems.list(params), rateItemsInstanceTotal: RateItems.count()]
    }

    def create() {
        [rateItemsInstance: new RateItems(params)]
    }

    def save() {
        def rateItemsInstance = new RateItems(params)
        if (!rateItemsInstance.save(flush: true)) {
            render(view: "create", model: [rateItemsInstance: rateItemsInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'rateItems.label', default: 'RateItems'), rateItemsInstance.id])
        redirect(action: "show", id: rateItemsInstance.id)
    }

    def show(Long id) {
        def rateItemsInstance = RateItems.get(id)
        if (!rateItemsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rateItems.label', default: 'RateItems'), id])
            redirect(action: "list")
            return
        }

        [rateItemsInstance: rateItemsInstance]
    }

    def edit(Long id) {
        def rateItemsInstance = RateItems.get(id)
        if (!rateItemsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rateItems.label', default: 'RateItems'), id])
            redirect(action: "list")
            return
        }

        [rateItemsInstance: rateItemsInstance]
    }

    def update(Long id, Long version) {
        def rateItemsInstance = RateItems.get(id)
        if (!rateItemsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rateItems.label', default: 'RateItems'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (rateItemsInstance.version > version) {
                rateItemsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'rateItems.label', default: 'RateItems')] as Object[],
                          "Another user has updated this RateItems while you were editing")
                render(view: "edit", model: [rateItemsInstance: rateItemsInstance])
                return
            }
        }

        rateItemsInstance.properties = params

        if (!rateItemsInstance.save(flush: true)) {
            render(view: "edit", model: [rateItemsInstance: rateItemsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'rateItems.label', default: 'RateItems'), rateItemsInstance.id])
        redirect(action: "show", id: rateItemsInstance.id)
    }

    def delete(Long id) {
        def rateItemsInstance = RateItems.get(id)
        if (!rateItemsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'rateItems.label', default: 'RateItems'), id])
            redirect(action: "list")
            return
        }

        try {
            rateItemsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'rateItems.label', default: 'RateItems'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'rateItems.label', default: 'RateItems'), id])
            redirect(action: "show", id: id)
        }
    }
}
